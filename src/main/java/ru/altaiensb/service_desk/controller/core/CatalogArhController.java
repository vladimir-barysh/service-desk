package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.CatalogArh;
import ru.altaiensb.service_desk.service.core.CatalogArhService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogarh")
@RequiredArgsConstructor
public class CatalogArhController {
    private final CatalogArhService service;

    @GetMapping
    public List<CatalogArh> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogArh> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
