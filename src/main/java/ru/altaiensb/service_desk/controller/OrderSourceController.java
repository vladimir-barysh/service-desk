package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.OrderSource;
import ru.altaiensb.service_desk.service.OrderSourceService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/ordersource")
@RequiredArgsConstructor
public class OrderSourceController {
    private final OrderSourceService service;

    @GetMapping
    public List<OrderSource> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderSource> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}