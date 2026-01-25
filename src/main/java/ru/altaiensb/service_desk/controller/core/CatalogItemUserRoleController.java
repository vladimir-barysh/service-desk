package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.CatalogItemUserRole;
import ru.altaiensb.service_desk.service.core.CatalogItemUserRoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogitemuserrole")
@RequiredArgsConstructor
public class CatalogItemUserRoleController {
    private final CatalogItemUserRoleService service;

    @GetMapping
    public List<CatalogItemUserRole> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogItemUserRole> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
