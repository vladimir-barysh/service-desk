package ru.altaiensb.service_desk.controller.reference;

import ru.altaiensb.service_desk.model.reference.Function;
import ru.altaiensb.service_desk.service.reference.FunctionService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/function")
@RequiredArgsConstructor
public class FunctionController {
    private final FunctionService service;

    @GetMapping
    public List<Function> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Function> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}