package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.Serv;
import ru.altaiensb.service_desk.service.core.ServiceService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/service")
@RequiredArgsConstructor
public class ServiceController {
    private final ServiceService service;

    @GetMapping
    public List<Serv> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Serv> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
