package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.FactLocation;
import ru.altaiensb.service_desk.service.FactLocationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/factlocation")
@RequiredArgsConstructor
public class FactLocationController {
    private final FactLocationService service;

    @GetMapping
    public List<FactLocation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<FactLocation> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}