package ru.altaiensb.service_desk.dto.CatalogItemDTO;

import java.time.LocalDate;

public record CatalogItemResponseDTO(
        Integer idCatitem,
        String nomer,
        String name,
        String description,
        String info,
        String expBasis,
        LocalDate expDate,
        String expOutBasis,
        LocalDate expOutDate,
        Integer parentId,
        Integer expTypeId,
        Integer effectId,
        Integer scaleId,
        Integer catitemStateId
) {}