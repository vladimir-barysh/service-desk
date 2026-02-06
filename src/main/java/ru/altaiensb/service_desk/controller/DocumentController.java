package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Document;
import ru.altaiensb.service_desk.service.DocumentService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/document")
@RequiredArgsConstructor
public class DocumentController {
    private final DocumentService service;

    @GetMapping
    public List<Document> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
