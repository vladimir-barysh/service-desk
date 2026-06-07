package ru.altaiensb.service_desk.dto.OrderTypeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.altaiensb.service_desk.annotation.AllFieldsRequired;

@AllFieldsRequired
public record OrderTypeResponseDTO(
        Integer idOrderType,
        String name,
        @Schema(nullable = true) Boolean available) {
}