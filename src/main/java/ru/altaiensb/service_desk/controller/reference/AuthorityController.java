package ru.altaiensb.service_desk.controller.reference;

import ru.altaiensb.service_desk.model.reference.Authority;
import ru.altaiensb.service_desk.service.reference.AuthorityService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/authorities")
@RequiredArgsConstructor
public class AuthorityController {
    private final AuthorityService service;

    @GetMapping
    public List<Authority> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Authority> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}