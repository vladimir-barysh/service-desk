package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.CatitemState;
import ru.altaiensb.service_desk.service.CatitemStateService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/catitemstate")
@RequiredArgsConstructor
public class CatitemStateController {
    private final CatitemStateService service;

    @GetMapping
    public List<CatitemState> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatitemState> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}