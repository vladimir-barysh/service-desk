package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.UserAuthority;
import ru.altaiensb.service_desk.service.UserAuthorityService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userauthoritie")
@RequiredArgsConstructor
public class UserAuthorityController {
    private final UserAuthorityService service;

    @GetMapping
    public List<UserAuthority> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserAuthority> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
