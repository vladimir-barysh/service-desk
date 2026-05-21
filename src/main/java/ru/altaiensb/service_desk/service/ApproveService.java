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
    public List<ApproveCandidateResponseDTO> getApproveCandidateByServiceId(Integer serviceId) {
        List<CatalogItemUserRole> roles = catalogItemUserRoleRepo.findByService_IdService(serviceId);
        return roles.stream()
                .map(role -> new ApproveCandidateResponseDTO(
                role.getUser().getIdItUser(),
                role.getUser().getFio1c(),
                role.getUserRole().getName(),
                role.getPodr() != null ? role.getPodr().getName() : null
                ))
                .distinct() // по idUser
                .collect(Collectors.toList());
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

    @Transactional
    public ApproveResponseDTO create(ApproveCreateRequestDTO dto) {
        // Загрузка обязательных связей из DTO
        Order order = orderRepo.findById(dto.idOrder())
                .orElseThrow(() -> new ResourceNotFoundException("Order", dto.idOrder()));
        // TODO: взять создателя заявки из контекста
        User creator = userRepo.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("User (creator)", 1));

        /* 
        Добавление пользователей в согласование 
        */
        String orderType = order.getOrderType().getName();
        boolean hasUsers = dto.userIds() != null && !dto.userIds().isEmpty();

        // Проверка соответствия
        if (hasUsers) {
                // Если переданы пользователи – тип должен быть ЗНО или ЗНТ
                if (!"ЗНО".equals(orderType) && !"ЗНТ".equals(orderType)) {
                        throw new IllegalArgumentException("Ручной выбор согласующих допустим только для заявок типа ЗНО или ЗНТ");
                }
        } else {
                // Если пользователи не переданы – тип должен быть ЗНД или ЗНИ (автоматическое создание)
                if (!"ЗНД".equals(orderType) && !"ЗНИ".equals(orderType)) {
                        throw new IllegalArgumentException("Для заявок типа " + orderType + " необходимо указать список согласующих");
                }
        }

        // Определяем список согласующих
        List<User> approvers;
        Integer serviceId = order.getService().getIdService();
        if (hasUsers) {
                approvers = userRepo.findAllById(dto.userIds());
                if (approvers.size() != dto.userIds().size()) {
                        throw new IllegalArgumentException("Некоторые из переданных пользователей не найдены");
                }
        } else {
                // Автоматический выбор для ЗНД и ЗНИ
                approvers = catalogItemUserRoleRepo.findByService_IdService(order.getService().getIdService())
                        .stream()
                        .filter(cir -> cir.getUserRole() != null && "Держатель сервиса".equals(cir.getUserRole().getName()))
                        .map(CatalogItemUserRole::getUser)
                        .distinct()
                        .collect(Collectors.toList());
                if (approvers.isEmpty()) {
                        throw new IllegalArgumentException("Для сервиса не настроены согласующие");
                }
        }
        
        
        // Значения по умолчанию
        OrderState defaultState = orderStateRepo.findByName("В ожидании")
                .orElseThrow(() -> new ResourceNotFoundException("OrderState", "В ожидании"));
/*         Instant datePlan = WorkingHoursUtil.addWorkHours(Instant.now(), 24); */

        // Постройка сущностей
        Approve approve = Approve.builder()
                .order(order)
                .name("Согласование по " + orderType + " №" + order.getNomer())
                .userCreator(creator)
                .flagApproved(false)
                .approveState(defaultState)
                .dateCreated(Instant.now())
/*                 .datePlan(datePlan) */
                .build();
        Approve savedApprove = approveRepo.save(approve);
        entityManager.refresh(savedApprove);

        List<ApproveUser> approveUsers = new ArrayList<>();
        for (User approver : approvers) {
                Integer userId = approver.getIdItUser();
                UserRole role = catalogItemUserRoleRepo
                        .findByService_IdServiceAndUser_IdItUser(serviceId, userId)
                        .map(CatalogItemUserRole::getUserRole)
                        .orElseThrow(() -> new ResourceNotFoundException("Роль для сервиса" + serviceId + " и пользователя " + userId));

                ApproveUser approveUser = ApproveUser.builder()
                        .approve(savedApprove)
                        .user(approver)
                        .userRole(role)
                        .state((short) 0)
/*                         .datePlan(datePlan) */
                        .build();
                approveUsers.add(approveUser);
        }
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
        List<ApproveUser> approveUsers = approveUserRepo.findByApprove_IdApprove(approveId);
        for (ApproveUser user : approveUsers) {
                user.setDatePlan(datePlan);
        }
        approveUserRepo.saveAll(approveUsers);
        
        Approve saved = approveRepo.save(approve);
        return toResponse(saved);
    }

    // ---------------------------- DELETE ----------------------------
    @Transactional
    public void delete(Integer approveId) {
        Approve approve = approveRepo.findById(approveId)
                .orElseThrow(() -> new ResourceNotFoundException("Approve", approveId));
        approveRepo.delete(approve);
    }
}