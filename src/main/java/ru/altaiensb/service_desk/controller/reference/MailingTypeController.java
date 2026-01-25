package ru.altaiensb.service_desk.controller.reference;

import ru.altaiensb.service_desk.model.reference.MailingType;
import ru.altaiensb.service_desk.service.reference.MailingTypeService;

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