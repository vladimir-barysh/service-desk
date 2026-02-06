package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Work;
import ru.altaiensb.service_desk.service.WorkService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work")
@RequiredArgsConstructor
public class WorkController {
    private final WorkService service;

    @GetMapping
    public List<Work> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Work> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
