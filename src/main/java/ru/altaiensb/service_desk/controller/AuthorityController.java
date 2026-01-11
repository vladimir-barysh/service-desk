package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Authority;
import ru.altaiensb.service_desk.repository.AuthorityRepository;
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
    private final AuthorityRepository authorityRepository;

    @GetMapping
    public List<Authority> getAll() {
        return authorityRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Authority> getById(@PathVariable Integer id) {
        return authorityRepository.findById(id).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
}
