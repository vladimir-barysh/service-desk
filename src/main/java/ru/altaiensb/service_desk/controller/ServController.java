package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.dto.ServDTO.ServResponseDTO;
import ru.altaiensb.service_desk.service.ServService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class ServController {
    private final ServService service;

    @GetMapping
    public List<ServResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
