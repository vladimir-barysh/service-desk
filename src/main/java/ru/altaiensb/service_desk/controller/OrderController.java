package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.service.OrderService;
import ru.altaiensb.service_desk.dto.OrderCreateDTO;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping
    public Order create(@RequestBody OrderCreateDTO dto) {
        return service.create(dto);
    }
}
