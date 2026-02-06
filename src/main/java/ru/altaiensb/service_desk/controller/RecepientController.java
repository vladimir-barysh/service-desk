package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Recepient;
import ru.altaiensb.service_desk.service.RecepientService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recepient")
@RequiredArgsConstructor
public class RecepientController {
    private final RecepientService service;

    @GetMapping
    public List<Recepient> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recepient> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
