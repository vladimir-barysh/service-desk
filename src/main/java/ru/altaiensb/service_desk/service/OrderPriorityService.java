package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.OrderPriorityDTO.OrderPriorityResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.OrderPriority;
import ru.altaiensb.service_desk.repository.OrderPriorityRepository;

@Service
@RequiredArgsConstructor
public class OrderPriorityService {

    private final OrderPriorityRepository repo;

    private OrderPriorityResponseDTO toResponse(OrderPriority priority) {
        return new OrderPriorityResponseDTO(
            priority.getIdOrderPriority(),
            priority.getName(),
            priority.getColor()
        );
    }

    @Transactional(readOnly = true)
    public List<OrderPriorityResponseDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderPriorityResponseDTO getById(Integer id) {
        OrderPriority priority = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderPriority", id));
        return toResponse(priority);
    }
}
