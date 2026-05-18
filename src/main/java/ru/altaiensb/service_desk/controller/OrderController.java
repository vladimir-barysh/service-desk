package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.service.OrderService;
import ru.altaiensb.service_desk.dto.OrderDTO.OrderResponseDTO;
import ru.altaiensb.service_desk.dto.OrderDTO.OrderUpdateDTO;
import ru.altaiensb.service_desk.dto.OrderDTO.OrderStatusUpdateDTO;
import ru.altaiensb.service_desk.dto.OrderDTO.OrderCreateRequestDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<OrderResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")  
    public ResponseEntity<OrderResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderCreateRequestDTO dto) {
        OrderResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable("id") Integer id, @RequestBody OrderUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Integer id, @RequestBody OrderStatusUpdateDTO statusDto) {
        return ResponseEntity.ok(service.updateStatus(id, statusDto.getIdOrderState()));
    }
}
