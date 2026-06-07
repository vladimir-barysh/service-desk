package ru.altaiensb.service_desk.dto.OrderPriorityDTO;

import ru.altaiensb.service_desk.annotation.AllFieldsRequired;

@AllFieldsRequired
public record OrderPriorityResponseDTO(
        Integer idOrderPriority,
        String name,
        String color) {
}