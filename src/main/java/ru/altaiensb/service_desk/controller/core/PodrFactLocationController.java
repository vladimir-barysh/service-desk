package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.PodrFactLocation;
import ru.altaiensb.service_desk.service.core.PodrFactLocationService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/podrfactlocation")
@RequiredArgsConstructor
public class PodrFactLocationController {
    private final PodrFactLocationService service;

    @GetMapping
    public List<PodrFactLocation> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PodrFactLocation> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
