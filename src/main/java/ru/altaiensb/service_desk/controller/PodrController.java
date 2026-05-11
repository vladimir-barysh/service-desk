package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.dto.PodrDTO.PodrResponseDTO;
import ru.altaiensb.service_desk.service.PodrService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/podr")
@RequiredArgsConstructor
public class PodrController {
    private final PodrService service;

    @GetMapping
    public List<PodrResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodrResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}