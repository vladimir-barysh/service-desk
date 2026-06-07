package ru.altaiensb.service_desk.dto.PodrDTO;

import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.altaiensb.service_desk.annotation.AllFieldsRequired;

@AllFieldsRequired
public record PodrResponseDTO(
        Integer idPodr,
        String name,
        @Schema(nullable = true) Integer parentId,
        @Schema(nullable = true) String id1c,
        Boolean isDeleted,
        @Schema(nullable = true) Integer por,
        Set<Integer> factLocationIds
) {}