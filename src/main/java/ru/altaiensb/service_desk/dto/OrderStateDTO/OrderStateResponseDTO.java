package ru.altaiensb.service_desk.dto.OrderStateDTO;

import ru.altaiensb.service_desk.annotation.AllFieldsRequired;

@AllFieldsRequired
public record OrderStateResponseDTO(
    Integer idOrderState,
    String name
) {}