package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.CatalogItem;
import ru.altaiensb.service_desk.service.core.CatalogItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogitem")
@RequiredArgsConstructor
public class CatalogItemController {
    private final CatalogItemService service;

    @GetMapping
    public List<CatalogItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogItem> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
