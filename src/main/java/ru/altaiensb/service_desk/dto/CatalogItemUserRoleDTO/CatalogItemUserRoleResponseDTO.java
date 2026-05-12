package ru.altaiensb.service_desk.dto.CatalogItemUserRoleDTO;

public record CatalogItemUserRoleResponseDTO(
        Integer idCatitemUserRole,
        Integer catalogItemId,
        Integer userId,
        Integer podrId,
        Integer userRoleId,
        Integer serviceId
) {}