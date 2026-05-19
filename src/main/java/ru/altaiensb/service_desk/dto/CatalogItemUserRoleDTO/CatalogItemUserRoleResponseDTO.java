package ru.altaiensb.service_desk.dto.CatalogItemUserRoleDTO;

import ru.altaiensb.service_desk.annotation.AllFieldsRequired;

@AllFieldsRequired
public record CatalogItemUserRoleResponseDTO(
        Integer idCatitemUserRole,
        Integer catalogItemId,
        Integer userId,
        Integer podrId,
        Integer userRoleId,
        Integer serviceId
) {}