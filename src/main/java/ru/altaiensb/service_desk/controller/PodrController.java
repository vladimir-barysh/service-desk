package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Podr;
import ru.altaiensb.service_desk.service.PodrService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/podr")
@RequiredArgsConstructor
public class PodrController {
    private final PodrService service;

    @GetMapping
    public List<Podr> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Podr> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}