package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.ServiceType;
import ru.altaiensb.service_desk.service.ServiceTypeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/servicetype")
@RequiredArgsConstructor
public class ServiceTypeController {
    private final ServiceTypeService service;

    @GetMapping
    public List<ServiceType> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceType> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}