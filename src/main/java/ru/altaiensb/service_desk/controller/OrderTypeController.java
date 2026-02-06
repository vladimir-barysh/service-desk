package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.OrderType;
import ru.altaiensb.service_desk.service.OrderTypeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/ordertype")   
@RequiredArgsConstructor
public class OrderTypeController {
    private final OrderTypeService service;

    @GetMapping
    public List<OrderType> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderType> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}