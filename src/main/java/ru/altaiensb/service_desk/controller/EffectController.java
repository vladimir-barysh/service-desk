package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Effect;
import ru.altaiensb.service_desk.service.EffectService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/effect")
@RequiredArgsConstructor
public class EffectController {
    private final EffectService service;

    @GetMapping
    public List<Effect> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Effect> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}