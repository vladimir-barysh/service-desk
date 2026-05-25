package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.OrderStateDTO.StateResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.OrderState;
import ru.altaiensb.service_desk.repository.OrderStateRepository;

@Service
@RequiredArgsConstructor
public class OrderStateService {

    private final OrderStateRepository repo;

    private StateResponseDTO toResponse(OrderState state) {
        return new StateResponseDTO(
            state.getIdOrderState(),
            state.getName()
        );
    }

    @Transactional(readOnly = true)
    public List<StateResponseDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public StateResponseDTO getById(Integer id) {
        OrderState state = repo.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("State", id));
        return toResponse(state);
    }
}
