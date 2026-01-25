package ru.altaiensb.service_desk.controller.reference;

import ru.altaiensb.service_desk.model.reference.Scale;
import ru.altaiensb.service_desk.service.reference.ScaleService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/scale")
@RequiredArgsConstructor
public class ScaleController {
    private final ScaleService service;

    @GetMapping
    public List<Scale> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Scale> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}