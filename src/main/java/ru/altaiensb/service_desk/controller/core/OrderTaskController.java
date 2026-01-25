package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.OrderTask;
import ru.altaiensb.service_desk.service.core.OrderTaskService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordertask")
@RequiredArgsConstructor
public class OrderTaskController {
    private final OrderTaskService service;

    @GetMapping
    public List<OrderTask> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderTask> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
