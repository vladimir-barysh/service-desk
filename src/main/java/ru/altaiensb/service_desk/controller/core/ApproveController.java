package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.Approve;
import ru.altaiensb.service_desk.service.core.ApproveService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approve")
@RequiredArgsConstructor
public class ApproveController {
    private final ApproveService service;

    @GetMapping
    public List<Approve> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Approve> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
