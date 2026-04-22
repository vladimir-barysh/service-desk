package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveCreateRequestDTO;
import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.Approve;
import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.model.User;
import ru.altaiensb.service_desk.model.OrderState;
import ru.altaiensb.service_desk.repository.ApproveRepository;
import ru.altaiensb.service_desk.repository.OrderRepository;
import ru.altaiensb.service_desk.repository.UserRepository;
import ru.altaiensb.service_desk.repository.OrderStateRepository;

@Service
@RequiredArgsConstructor
public class ApproveService {
    private final ApproveRepository repo;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final OrderStateRepository orderStateRepository;

    // Преобразование сущности в DTO
    private ApproveResponseDTO toResponse(Approve approve) {
        return new ApproveResponseDTO(
                approve.getIdApprove(),
                approve.getOrder() != null ? approve.getOrder().getIdOrder() : null,
                approve.getName(),
                approve.getUserCreator() != null ? approve.getUserCreator().getIdItUser() : null,
                approve.getFlagApproved(),
                approve.getDateCreated(),
                approve.getDatePlan(),
                approve.getApproveState() != null ? approve.getApproveState().getIdOrderState() : null,
                approve.getDateFact(),
                approve.getTaskText()
        );
    }

    @Transactional(readOnly = true)
    public List<ApproveResponseDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public ApproveResponseDTO getById(Integer id) {
        Approve approve = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Approve", id));
        return toResponse(approve);
    }

    @Transactional
    public ApproveResponseDTO create(ApproveCreateRequestDTO dto) {
        // Находим связанные сущности
        Order order = orderRepository.findById(dto.idOrder())
                .orElseThrow(() -> new ResourceNotFoundException("Order", dto.idOrder()));
        User creator = userRepository.findById(dto.idUserCreator())
                .orElseThrow(() -> new ResourceNotFoundException("User", dto.idUserCreator()));
        OrderState state = orderStateRepository.findById(dto.idApproveState())
                .orElseThrow(() -> new ResourceNotFoundException("OrderState", dto.idApproveState()));

        Approve approve = Approve.builder()
                .order(order)
                .name(dto.name())
                .userCreator(creator)
                .flagApproved(false)
                .approveState(state)
                .dateCreated(Instant.now())
                .datePlan(dto.datePlan())
                .taskText(dto.taskText())
                .build();

        Approve saved = repo.save(approve);
        return toResponse(saved);
    }
}