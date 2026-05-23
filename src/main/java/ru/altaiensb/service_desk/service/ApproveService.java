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
import ru.altaiensb.service_desk.repository.ApproveRepository;
import ru.altaiensb.service_desk.repository.ApproveUserRepository;
import ru.altaiensb.service_desk.repository.CatalogItemUserRoleRepository;
import ru.altaiensb.service_desk.repository.OrderRepository;
import ru.altaiensb.service_desk.repository.UserRepository;
import ru.altaiensb.service_desk.repository.UserRoleRepository;
import ru.altaiensb.service_desk.util.WorkingHoursUtil;
import ru.altaiensb.service_desk.repository.OrderStateRepository;

@Service
@RequiredArgsConstructor
public class ApproveService {
    @PersistenceContext private EntityManager entityManager; 
    private final ApproveRepository approveRepo;
    private final OrderRepository orderRepo;
    private final UserRepository userRepo;
    private final OrderStateRepository orderStateRepo;
    private final CatalogItemUserRoleRepository catalogItemUserRoleRepo;
    private final ApproveUserRepository approveUserRepo;
    private final UserRoleRepository userRoleRepo;

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
                        .state((short) 0)
                        .build())
                .collect(Collectors.toList());
        approveUserRepo.saveAll(approveUsers);

        return toResponse(savedApprove);
    }

    // ---------------------------- UPDATE ----------------------------
    @Transactional
    public ApproveResponseDTO startProcess(Integer approveId) {
        Approve approve = approveRepo.findById(approveId)
                .orElseThrow(() -> new ResourceNotFoundException("Approve", approveId));
        
        // Проверка, не запущено ли ещё это согласование
        if (approve.getApproveState().getIdOrderState() == 7) {
                throw new IllegalStateException("Согласование уже запущено");
        }
        
        // Меняем статус на "На согласовании" (id = 7)
        OrderState inProgressState = orderStateRepo.findById(7)
                .orElseThrow(() -> new ResourceNotFoundException("OrderState", 7));
        approve.setApproveState(inProgressState);
        
        // Устанавливаем плановую дату через 24 рабочих часа
        Instant datePlan = WorkingHoursUtil.addWorkHours(Instant.now(), 24);
        approve.setDatePlan(datePlan);

        // Обновляем плановую дату у всех участников этого согласования
        // TODO: возможно обновлять для всех, кто ещё не согласовал
        List<ApproveUser> approveUsers = approveUserRepo.findByApprove_IdApprove(approveId);
        for (ApproveUser user : approveUsers) {
                user.setDatePlan(datePlan);
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
        approveUserRepo.deleteAll(toRemove);
        
        // Кого добавить
        List<Integer> toAdd = targetIds.stream()
                .filter(id -> !currentIds.contains(id))
                .collect(Collectors.toList());

        if (!toRemove.isEmpty() || !toAdd.isEmpty()) {
            // Переводим согласование в статус "В ожидании" и сбрасываем флаги
            OrderState waitingState = orderStateRepo.findByName("В ожидании")
                    .orElseThrow(() -> new ResourceNotFoundException("OrderState", "В ожидании"));

            approve.setApproveState(waitingState);
            approve.setFlagApproved(false);
            approve.setDateFact(null);

            approveRepo.save(approve);
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
                        .state((short) 0)
                        .build();
                approveUserRepo.save(approveUser);
            }
        }
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