package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveCreateRequestDTO;
import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveResponseDTO;
import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveCandidateResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.Approve;
import ru.altaiensb.service_desk.model.CatalogItemUserRole;
import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.model.User;
import ru.altaiensb.service_desk.model.OrderState;
import ru.altaiensb.service_desk.repository.ApproveRepository;
import ru.altaiensb.service_desk.repository.CatalogItemUserRoleRepository;
import ru.altaiensb.service_desk.repository.OrderRepository;
import ru.altaiensb.service_desk.repository.UserRepository;
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
    @Transactional
    public ApproveResponseDTO create(ApproveCreateRequestDTO dto) {
        // Загрузка обязательных связей из DTO
        Order order = orderRepo.findById(dto.idOrder())
                .orElseThrow(() -> new ResourceNotFoundException("Order", dto.idOrder()));
        User creator = userRepo.findById(dto.idUserCreator())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.idUserCreator()));

        // Значения по умолчанию
        OrderState defaultState = orderStateRepo.findByName("На согласовании")
                .orElseThrow(() -> new ResourceNotFoundException("OrderState", "На согласовании"));

        Approve approve = Approve.builder()
                .order(order)
                .name(dto.name())
                .userCreator(creator)
                .flagApproved(false)
                .approveState(defaultState)
                .dateCreated(Instant.now())
                .datePlan(dto.datePlan())
                .taskText(dto.taskText())
                .build();

        Approve saved = approveRepo.save(approve);
        entityManager.refresh(saved);
        return toResponse(saved);
    }
}