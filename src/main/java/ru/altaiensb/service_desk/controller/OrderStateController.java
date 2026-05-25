package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.dto.OrderStateDTO.OrderStateResponseDTO;
import ru.altaiensb.service_desk.service.OrderStateService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderstate")
@RequiredArgsConstructor
public class OrderStateController {
    private final OrderStateService service;

    @GetMapping
    public List<OrderStateResponseDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderStateResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}