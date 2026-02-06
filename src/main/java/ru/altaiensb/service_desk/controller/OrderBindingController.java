package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.OrderBinding;
import ru.altaiensb.service_desk.service.OrderBindingService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orderbinding")
@RequiredArgsConstructor
public class OrderBindingController {
    private final OrderBindingService service;

    @GetMapping
    public List<OrderBinding> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderBinding> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
