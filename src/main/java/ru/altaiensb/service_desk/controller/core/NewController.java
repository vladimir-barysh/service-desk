package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.New;
import ru.altaiensb.service_desk.service.core.NewService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/new")
@RequiredArgsConstructor
public class NewController {
    private final NewService service;

    @GetMapping
    public List<New> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<New> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
