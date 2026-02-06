package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.CatalogArhData;
import ru.altaiensb.service_desk.service.CatalogArhDataService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogarhdata")
@RequiredArgsConstructor
public class CatalogArhDataController {
    private final CatalogArhDataService service;

    @GetMapping
    public List<CatalogArhData> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogArhData> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
