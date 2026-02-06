package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Menu;
import ru.altaiensb.service_desk.service.MenuService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
public class MenuController {
    private final MenuService service;

    @GetMapping
    public List<Menu> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Menu> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}