package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.CatalogItemUserRoleRepository;
import ru.altaiensb.service_desk.model.core.CatalogItemUserRole;

@Service
@RequiredArgsConstructor
public class CatalogItemUserRoleService {

    private final CatalogItemUserRoleRepository repo;

    public List<CatalogItemUserRole> getAll() {
        return repo.findAll();
    }

    public CatalogItemUserRole getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("CatalogItemUserRole not found with id=" + id));
    }
}
