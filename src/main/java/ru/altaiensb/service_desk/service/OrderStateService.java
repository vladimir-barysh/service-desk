package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.OrderStateDTO.OrderStateResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.OrderState;
import ru.altaiensb.service_desk.repository.OrderStateRepository;

@Service
@RequiredArgsConstructor
public class OrderStateService {
    private final OrderStateRepository orderStateRepo;

    // ---------------------------- Respons ----------------------------
    private OrderStateResponseDTO toResponse(OrderState state) {
        return new OrderStateResponseDTO(
            state.getIdOrderState(), 
            state.getName()
        );
    }

    // ---------------------------- READ ----------------------------
    @Transactional(readOnly = true)
    public List<OrderStateResponseDTO> getAll() {
        return orderStateRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderStateResponseDTO getById(Integer id) {
        OrderState state = orderStateRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderState", id));
        return toResponse(state);
    }
}