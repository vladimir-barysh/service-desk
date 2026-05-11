package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.dto.AuthorityDTO.AuthorityResponseDTO;
import ru.altaiensb.service_desk.service.AuthorityService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authority")
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthorityService service;

    @GetMapping
    public List<AuthorityResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuthorityResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}