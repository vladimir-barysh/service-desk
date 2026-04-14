package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Approve;
import ru.altaiensb.service_desk.service.ApproveService;
import ru.altaiensb.service_desk.dto.ApproveCreateRequestDTO;
import ru.altaiensb.service_desk.dto.ApproveResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/approve")
@RequiredArgsConstructor
public class ApproveController {
    private final ApproveService service;

    @GetMapping
    public List<ApproveResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApproveResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
    
    @PostMapping
    public ResponseEntity<ApproveResponseDTO> create(@Valid @RequestBody ApproveCreateRequestDTO dto) {
        ApproveResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
