package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.model.CatalogItemUserRole;
import ru.altaiensb.service_desk.dto.CatalogItemUserRoleDTO.CatalogItemUserRoleResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.repository.CatalogItemUserRoleRepository;

@Service
@RequiredArgsConstructor
public class CatalogItemUserRoleService {
    private final CatalogItemUserRoleRepository CatalogItemUserRoleRepo;

    // ---------------------------- Respons ----------------------------
    private CatalogItemUserRoleResponseDTO toResponse(CatalogItemUserRole entity) {
        return new CatalogItemUserRoleResponseDTO(
            entity.getIdCatitemUserRole(),
            entity.getCatalogItem() != null ? entity.getCatalogItem().getIdCatitem() : null,
            entity.getUser() != null ? entity.getUser().getIdItUser() : null,
            entity.getPodr() != null ? entity.getPodr().getIdPodr() : null,
            entity.getUserRole() != null ? entity.getUserRole().getIdUserRole() : null,
            entity.getService() != null ? entity.getService().getIdService() : null
        );
    }

    // ---------------------------- READ ----------------------------
    @Transactional(readOnly = true)
    public List<CatalogItemUserRoleResponseDTO> getAll() {
        return CatalogItemUserRoleRepo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CatalogItemUserRoleResponseDTO getById(Integer id) {
        CatalogItemUserRole entity = CatalogItemUserRoleRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("CatalogItemUserRole", id));
        return toResponse(entity);
    }
}