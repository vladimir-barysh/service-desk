package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.OrderTypeDTO.OrderTypeResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.OrderType;
import ru.altaiensb.service_desk.repository.OrderTypeRepository;

@Service
@RequiredArgsConstructor
public class OrderTypeService {

    private final OrderTypeRepository repo;

    private OrderTypeResponseDTO toResponse(OrderType type) {
        return new OrderTypeResponseDTO(
            type.getIdOrderType(), 
            type.getName(),
            type.getAvailable()
        );
    };

    @Transactional(readOnly = true)
    public List<OrderTypeResponseDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderTypeResponseDTO getById(Integer id) {
        OrderType type = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OrderType", id));
        return toResponse(type);
    }
}
