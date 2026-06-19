package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveCreateRequestDTO;
import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveResponseDTO;
import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveCandidateResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.Approve;
import ru.altaiensb.service_desk.model.ApproveUser;
import ru.altaiensb.service_desk.model.CatalogItemUserRole;
import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.model.User;
import ru.altaiensb.service_desk.model.UserRole;
import ru.altaiensb.service_desk.model.OrderState;
import ru.altaiensb.service_desk.model.OrderTask;
import ru.altaiensb.service_desk.repository.ApproveRepository;
import ru.altaiensb.service_desk.repository.ApproveUserRepository;
import ru.altaiensb.service_desk.repository.CatalogItemUserRoleRepository;
import ru.altaiensb.service_desk.repository.OrderRepository;
import ru.altaiensb.service_desk.repository.UserRepository;
import ru.altaiensb.service_desk.repository.UserRoleRepository;
import ru.altaiensb.service_desk.util.WorkingHoursUtil;
import ru.altaiensb.service_desk.repository.OrderStateRepository;
import ru.altaiensb.service_desk.repository.OrderTaskRepository;

@Service
@RequiredArgsConstructor
public class ApproveService {
    @PersistenceContext private EntityManager entityManager; 
    private final ApproveRepository approveRepo;
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final OrderTaskRepository taskRepo;
    private final OrderStateRepository orderStateRepo;
    private final CatalogItemUserRoleRepository catalogItemUserRoleRepo;
    private final ApproveUserRepository approveUserRepo;
    private final UserRoleRepository userRoleRepo;

    private static final String PENDING_APPROVAL = "На согласовании";
    private static final String APPROVED = "Согласовано";
    private static final String NOT_APPROVED = "Не согласовано";

    // ---------------------------- Respons ----------------------------
    private ApproveResponseDTO toResponse(Approve approve) {
        return new ApproveResponseDTO(
                approve.getIdApprove(),
                approve.getOrder() != null ? approve.getOrder().getIdOrder() : null,
                approve.getName(),
                approve.getUserCreator() != null ? approve.getUserCreator().getIdItUser() : null,
                approve.getFlagApproved(),
                approve.getApproveState() != null ? approve.getApproveState().getIdOrderState() : null,
                approve.getDateCreated(),
                approve.getDatePlan(),
                approve.getDateFact(),
                approve.getTaskText()
        );
    }

    // ---------------------------- READ ----------------------------
    @Transactional(readOnly = true)
    public List<ApproveResponseDTO> getByOrderId(Integer orderId) {
        List<Approve> approve = approveRepo.findByOrder_IdOrder(orderId);
        return approve.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApproveResponseDTO getById(Integer id) {
        Approve approve = approveRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Approve", id));
        return toResponse(approve);
    }

    @Transactional(readOnly = true)
    public List<ApproveCandidateResponseDTO> getCandidatesForOrder(Integer orderId) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
        String orderTypeName = order.getOrderType().getName();
        Integer serviceId = order.getService().getIdService();

        if ("ЗНД".equals(orderTypeName)) {
                // Кандидаты по сервису из it_catitem_user_role
                List<CatalogItemUserRole> roles = catalogItemUserRoleRepo.findByService_IdService(serviceId);
                return roles.stream()
                        .map(role -> new ApproveCandidateResponseDTO(
                                role.getUser().getIdItUser(),
                                role.getUser().getFio1c(),
                                role.getUserRole().getName(),
                                role.getPodr() != null ? role.getPodr().getName() : null
                        ))
                        .distinct()
                        .collect(Collectors.toList());
        } else if ("ЗНИ".equals(orderTypeName) || "ЗНО".equals(orderTypeName) || "ЗНТ".equals(orderTypeName)) {
                // Все пользователи
                List<User> users = userRepo.findAll();
                return users.stream()
                        .map(user -> new ApproveCandidateResponseDTO(
                                user.getIdItUser(),
                                user.getFio1c(),
                                null, // роль определена только для пользователей сервиса
                                user.getPodr() != null ? user.getPodr().getName() : null
                        ))
                        .collect(Collectors.toList());
        } else {
                throw new IllegalArgumentException("Неизвестный тип заявки: " + orderTypeName);
        }
    }

    // ---------------------------- CREATE ----------------------------
    // Автоматическое создание согласования для ЗНД и ЗНИ
    @Transactional
    public ApproveResponseDTO createAuto(Order order) {
        ApproveCreateRequestDTO dto = new ApproveCreateRequestDTO(
                order.getIdOrder(),
                null
        );
        return create(dto);
    }

    private record ApproveUserCandidate(User user, UserRole role) {}

    @Transactional
    public ApproveResponseDTO create(ApproveCreateRequestDTO dto) {
        Order order = orderRepo.findById(dto.idOrder())
                .orElseThrow(() -> new ResourceNotFoundException("Order", dto.idOrder()));

        // TODO: взять создателя заявки из контекста
        User creator = userRepo.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("User (creator)", 1));

        String orderType = order.getOrderType().getName();
        boolean hasUsers = dto.userIds() != null && !dto.userIds().isEmpty();
        Integer serviceId = order.getService().getIdService();

        // Сборка пар (пользователь, роль)
        List<ApproveUserCandidate> candidates = new ArrayList<>();

        if (hasUsers) {
            // Ручной выбор
            List<User> users = userRepo.findAllById(dto.userIds());
            if (users.size() != dto.userIds().size()) {
                throw new IllegalArgumentException("Некоторые из переданных пользователей не найдены");
            }

            UserRole defaultRole = userRoleRepo.findByName("Согласующий")
                    .orElseThrow(() -> new ResourceNotFoundException("UserRole", "Согласующий"));

            for (User user : users) {
                UserRole role = defaultRole;
                if ("ЗНД".equals(orderType)) {
                    role = catalogItemUserRoleRepo
                            .findByService_IdServiceAndUser_IdItUser(serviceId, user.getIdItUser())
                            .map(CatalogItemUserRole::getUserRole)
                            .orElseThrow(() -> new ResourceNotFoundException("Роль для сервиса " + serviceId + " и пользователя " + user.getIdItUser()));
                }
                candidates.add(new ApproveUserCandidate(user, role));
            }
        } else {
            // Автоматическое создание (только для ЗНД и ЗНИ)
            if (!"ЗНД".equals(orderType) && !"ЗНИ".equals(orderType)) {
                throw new IllegalArgumentException("Для заявок типа " + orderType + " недоступно автоматическое создание");
            }

            List<CatalogItemUserRole> roles = catalogItemUserRoleRepo.findByService_IdService(serviceId)
                    .stream()
                    .filter(cir -> cir.getUserRole() != null && "Держатель сервиса".equals(cir.getUserRole().getName()))
                    .collect(Collectors.toList());
            if (roles.isEmpty()) {
                throw new IllegalArgumentException("Для сервиса не настроены согласующие с ролью 'Держатель сервиса'");
            }

            for (CatalogItemUserRole role : roles) {
                candidates.add(new ApproveUserCandidate(role.getUser(), role.getUserRole()));
            }
        }
        // Создание Approve
        OrderState defaultState = orderStateRepo.findByName("В ожидании")
                .orElseThrow(() -> new ResourceNotFoundException("OrderState", "В ожидании"));

        Approve approve = Approve.builder()
                .order(order)
                .name("Согласование по " + orderType + " №" + order.getNomer())
                .userCreator(creator)
                .flagApproved(false)
                .approveState(defaultState)
                .dateCreated(Instant.now())
                .build();
        Approve savedApprove = approveRepo.save(approve);

        // Создание ApproveUser
        List<ApproveUser> approveUsers = candidates.stream()
                .map(var -> ApproveUser.builder()
                        .approve(savedApprove)
                        .user(var.user())
                        .userRole(var.role())
                        .approveUserState(defaultState)
                        .build())
                .collect(Collectors.toList());
        approveUserRepo.saveAll(approveUsers);

        // Меняем статусы связанной заявки и задачи на "На согласовании"
        OrderState pendingApproval = orderStateRepo.findByName(PENDING_APPROVAL)
                .orElseThrow(() -> new ResourceNotFoundException("State"));

        order.setOrderState(pendingApproval);
        /* 
            Сейчас меняем статус только у одной задачи. 
            В перспективе проверять на что-нибудь, если будет много задач
        */
        OrderTask task = taskRepo.findByOrder_IdOrder(order.getIdOrder())
                            .orElseThrow(() -> new ResourceNotFoundException("Task"));
        task.setTaskState(pendingApproval);

        return toResponse(savedApprove);
    }

    // ---------------------------- UPDATE ----------------------------
    @Transactional
    public ApproveResponseDTO startProcess(Integer approveId) {
        Approve approve = approveRepo.findById(approveId)
                .orElseThrow(() -> new ResourceNotFoundException("Approve", approveId));

		// Загружаем нужные статусы из БД
		OrderState inProgressState = orderStateRepo.findByName("На согласовании")
				.orElseThrow(() -> new ResourceNotFoundException("OrderState", "На согласовании"));
		OrderState approvedState = orderStateRepo.findByName("Согласовано")
				.orElseThrow(() -> new ResourceNotFoundException("OrderState", "Согласовано"));

		// Проверяем, что согласование ещё не запущено
		if (approve.getApproveState().getIdOrderState().equals(inProgressState.getIdOrderState())) {
			throw new IllegalStateException("Согласование уже запущено");
		}
        
        // Переводим согласование в статус "На согласовании"
		approve.setApproveState(inProgressState);
		approve.setFlagApproved(false);
		approve.setDateFact(null);
		Instant datePlan = WorkingHoursUtil.addWorkHours(Instant.now(), 24);
		approve.setDatePlan(datePlan);

        // Обновляем участников
		List<ApproveUser> approveUsers = approveUserRepo.findByApprove_IdApprove(approveId);
		for (ApproveUser user : approveUsers) {
			// Уже согласовавших не трогаем
			if (user.getApproveUserState() != null 
					&& user.getApproveUserState().getIdOrderState().equals(approvedState.getIdOrderState())) {
				continue;
			}

			// Если диспетчер пометил участника как игнорируемого – не меняем его статус (его решение игнорируется)
			if (Boolean.TRUE.equals(user.getFlagIgnored())) {
				continue;
			}

			// Остальных переводим в "На согласовании" (включая тех, кто был "Не согласовано" или "В ожидании")
			user.setApproveUserState(inProgressState);
			user.setDatePlan(datePlan);
			user.setDateFact(null);
			user.setResultText(null);
		}
		approveUserRepo.saveAll(approveUsers);

		Approve saved = approveRepo.save(approve);
		return toResponse(saved);
	}

    @Transactional
    public void updateUsers(Integer approveId, List<Integer> newUserIds) {
        Approve approve = approveRepo.findById(approveId)
                .orElseThrow(() -> new ResourceNotFoundException("Approve", approveId));
        
        // Текущие участники
        List<ApproveUser> currentUsers = approveUserRepo.findByApprove_IdApprove(approveId);
        Set<Integer> currentIds = currentUsers.stream()
                .map(au -> au.getUser().getIdItUser())
                .collect(Collectors.toSet());
        Set<Integer> targetIds = new HashSet<>(newUserIds);
        
        // Кого удалить
        List<ApproveUser> toRemove = currentUsers.stream()
                .filter(au -> !targetIds.contains(au.getUser().getIdItUser()))
                .collect(Collectors.toList());
        
        // Кого добавить
        List<Integer> toAdd = targetIds.stream()
                .filter(id -> !currentIds.contains(id))
                .collect(Collectors.toList());

		OrderState waitingState = orderStateRepo.findByName("В ожидании")
			.orElseThrow(() -> new ResourceNotFoundException("OrderState", "В ожидании"));

		// Переводим в статус "В ожидании" и сбрасываем флаги
/*         if (!toRemove.isEmpty() || !toAdd.isEmpty()) { */
        approve.setApproveState(waitingState);
        approve.setFlagApproved(false);
        approve.setDateFact(null);
        approveRepo.save(approve);
        
        OrderState inProgressState = orderStateRepo.findByName("На согласовании")
            .orElseThrow(() -> new ResourceNotFoundException("OrderState", "На согласовании"));
            
        // Сбрасываем статус всех оставшихся участников со статусом "На согласовании"
        List<ApproveUser> remainingUsers = currentUsers.stream()
                .filter(au -> !toRemove.contains(au))
                .collect(Collectors.toList());
        for (ApproveUser au : remainingUsers) {
            if (au.getApproveUserState() != null 
                    && au.getApproveUserState().getIdOrderState().equals(inProgressState.getIdOrderState())) {
                au.setApproveUserState(waitingState);
                au.setDateFact(null);
                au.setResultText(null);
                au.setDatePlan(null);
            }
        }
/*         } */

		// Удаление
		if (!toRemove.isEmpty()) {
			approveUserRepo.deleteAll(toRemove);
		}
        
        // Добавление новых участников
        if (!toAdd.isEmpty()) {
            List<User> newUsers = userRepo.findAllById(toAdd);
            if (newUsers.size() != toAdd.size()) {
                throw new IllegalArgumentException("Некоторые пользователи не найдены");
            }

            Integer serviceId = approve.getOrder().getService().getIdService();
            UserRole defaultRole = userRoleRepo.findByName("Согласующий")
                    .orElseThrow(() -> new ResourceNotFoundException("UserRole", "Согласующий"));
            String orderType = approve.getOrder().getOrderType().getName();

            // Создание ApproveUser для новых участников
            for (User user : newUsers) {
                UserRole role = defaultRole;
                if ("ЗНД".equals(orderType)) {
                    role = catalogItemUserRoleRepo
                            .findByService_IdServiceAndUser_IdItUser(serviceId, user.getIdItUser())
                            .map(CatalogItemUserRole::getUserRole)
                            .orElseThrow(() -> new ResourceNotFoundException("Роль для сервиса " + serviceId + " и пользователя " + user.getIdItUser()));
                }
                
                ApproveUser approveUser = ApproveUser.builder()
                        .approve(approve)
                        .user(user)
                        .userRole(role)
                        .approveUserState(waitingState)
                        .build();
                approveUserRepo.save(approveUser);
            }
        }
    }

	// Пересчитываем общий статус согласования
	@Transactional
	public void recalculateStatus(Integer approveId) {
		Approve approve = approveRepo.findById(approveId)
						.orElseThrow(() -> new ResourceNotFoundException("Approve", approveId));

		List<ApproveUser> users = approveUserRepo.findByApprove_IdApprove(approveId);
		List<ApproveUser> activeUsers = users.stream()
				.filter(u -> !Boolean.TRUE.equals(u.getFlagIgnored()))
				.filter(u -> u.getApproveUserState() != null)
				.collect(Collectors.toList());

		// Загружаем статусы по имени
		OrderState approvedState = orderStateRepo.findByName(APPROVED)
				.orElseThrow(() -> new ResourceNotFoundException("OrderState", "Согласовано"));
		OrderState notApprovedState = orderStateRepo.findByName(NOT_APPROVED)
				.orElseThrow(() -> new ResourceNotFoundException("OrderState", "Не согласовано"));
		OrderState rejectedState = orderStateRepo.findByName("Согласование отклонено")
				.orElseThrow(() -> new ResourceNotFoundException("OrderState", "Согласование отклонено"));

		boolean allApproved = activeUsers.stream()
				.allMatch(u -> u.getApproveUserState().getIdOrderState().equals(approvedState.getIdOrderState()));
    	boolean anyNotApproved = activeUsers.stream()
				.anyMatch(u -> u.getApproveUserState().getIdOrderState().equals(notApprovedState.getIdOrderState()));
    	boolean anyRejected = activeUsers.stream()
				.anyMatch(u -> u.getApproveUserState().getIdOrderState().equals(rejectedState.getIdOrderState()));
		if (allApproved) {
			approve.setFlagApproved(true);
			approve.setDateFact(Instant.now());
			approve.setApproveState(approvedState);
		} else if (anyRejected) {
			approve.setFlagApproved(false);
			approve.setDateFact(Instant.now());
			approve.setApproveState(rejectedState);
		} else if (anyNotApproved) {
			approve.setFlagApproved(false);
			approve.setDateFact(Instant.now());
			approve.setApproveState(notApprovedState);
		}
		// else – статус не меняем

		approveRepo.save(approve);
	}

    // ---------------------------- DELETE ----------------------------
    @Transactional
    public void delete(Integer approveId) {
        Approve approve = approveRepo.findById(approveId)
            	.orElseThrow(() -> new ResourceNotFoundException("Approve", approveId));
        approveRepo.delete(approve);
    }

    @Transactional
	public List<ApproveResponseDTO> refreshByOrder(Integer orderId) {
    	// Удаляем все согласования для заявки (каскадно удалятся и approve_users)
        List<Approve> oldApproves = approveRepo.findByOrder_IdOrder(orderId);
        approveRepo.deleteAll(oldApproves);
        
		Order order = orderRepo.findById(orderId)
				.orElseThrow(() -> new ResourceNotFoundException("Order", orderId));
		
		// Автоматически создаём согласование для ЗНД и ЗНИ 
		String typeName = order.getOrderType().getName();
		if ("ЗНД".equals(typeName) || "ЗНИ".equals(typeName)) {
			ApproveResponseDTO newApprove = createAuto(order);
			return List.of(newApprove);
		} else {
			return Collections.emptyList();
		}
	}
}