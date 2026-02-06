package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.MailingType;
import ru.altaiensb.service_desk.service.MailingTypeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/mailingtype")
@RequiredArgsConstructor
public class MailingTypeController {
    private final MailingTypeService service;

    @GetMapping
    public List<MailingType> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MailingType> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}