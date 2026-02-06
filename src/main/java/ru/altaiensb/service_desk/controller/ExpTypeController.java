package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.ExpType;
import ru.altaiensb.service_desk.service.ExpTypeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/exptype")
@RequiredArgsConstructor
public class ExpTypeController {
    private final ExpTypeService service;

    @GetMapping
    public List<ExpType> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpType> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}