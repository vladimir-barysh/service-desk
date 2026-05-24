package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.dto.OrderTaskDTO.TaskCreateRequestDTO;
import ru.altaiensb.service_desk.dto.OrderTaskDTO.TaskResponseDTO;
import ru.altaiensb.service_desk.dto.OrderTaskDTO.TaskUpdateDTO;
import ru.altaiensb.service_desk.service.OrderTaskService;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/ordertask")
@RequiredArgsConstructor
public class OrderTaskController {
    private final OrderTaskService service;

    @GetMapping
    public List<TaskResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> getById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(@Valid @RequestBody TaskCreateRequestDTO dto) {
        TaskResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<TaskResponseDTO> update(@PathVariable("id") Integer id, @RequestBody TaskUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
}
