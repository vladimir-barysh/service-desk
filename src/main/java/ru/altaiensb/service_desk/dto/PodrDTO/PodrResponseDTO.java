package ru.altaiensb.service_desk.dto.PodrDTO;

import java.util.Set;

public record PodrResponseDTO(
        Integer idPodr,
        String name,
        Integer parentId,          // идентификатор родительского подразделения (может быть null)
        String id1c,
        Boolean isDeleted,
        Integer por,
        Set<Integer> factLocationIds
) {}