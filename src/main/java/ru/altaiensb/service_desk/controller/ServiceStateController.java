package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.ServiceState;
import ru.altaiensb.service_desk.service.ServiceStateService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/servicestate")
@RequiredArgsConstructor
public class ServiceStateController {
    private final ServiceStateService service;

    @GetMapping
    public List<ServiceState> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceState> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}