package ru.altaiensb.service_desk.controller.reference;

import ru.altaiensb.service_desk.model.reference.OrderPriority;
import ru.altaiensb.service_desk.service.reference.OrderPriorityService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/orderpriority")
@RequiredArgsConstructor
public class OrderPriorityController {
    private final OrderPriorityService service;

    @GetMapping
    public List<OrderPriority> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderPriority> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}