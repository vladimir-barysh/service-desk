package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.dto.CatalogItemUserRoleDTO.CatalogItemUserRoleResponseDTO;
import ru.altaiensb.service_desk.service.CatalogItemUserRoleService;

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
    public List<CatalogItemUserRoleResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogItemUserRoleResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
