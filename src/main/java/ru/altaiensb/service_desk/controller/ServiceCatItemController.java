package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.ServiceCatItem;
import ru.altaiensb.service_desk.service.ServiceCatItemService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicecatitem")
@RequiredArgsConstructor
public class ServiceCatItemController {
    private final ServiceCatItemService service;

    @GetMapping
    public List<ServiceCatItem> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceCatItem> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
