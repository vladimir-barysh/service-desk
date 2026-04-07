package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.dto.TaskUpdateDTO;
import ru.altaiensb.service_desk.model.OrderTask;
import ru.altaiensb.service_desk.service.OrderTaskService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    // TODO: реализовать создание задачи на сервере
    @PostMapping("path")
    public String postMethodName(@RequestBody String entity) {
        //TODO: process POST request
        
        return entity;
    }
    
    @PatchMapping("/{id}")
    public ResponseEntity<OrderTask> update(@PathVariable("id") Integer id, @RequestBody TaskUpdateDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }
}
