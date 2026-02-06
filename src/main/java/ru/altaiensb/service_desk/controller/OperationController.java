package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Operation;
import ru.altaiensb.service_desk.service.OperationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/operation")
@RequiredArgsConstructor
public class OperationController {
    private final OperationService service;

    @GetMapping
    public List<Operation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Operation> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}