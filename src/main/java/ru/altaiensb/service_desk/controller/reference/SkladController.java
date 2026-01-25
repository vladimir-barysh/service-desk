package ru.altaiensb.service_desk.controller.reference;

import ru.altaiensb.service_desk.model.reference.Sklad;
import ru.altaiensb.service_desk.service.reference.SkladService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/sklad")
@RequiredArgsConstructor
public class SkladController {
    private final SkladService service;

    @GetMapping
    public List<Sklad> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sklad> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}