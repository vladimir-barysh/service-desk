package ru.altaiensb.service_desk.controller.reference;

import ru.altaiensb.service_desk.model.reference.WorkType;
import ru.altaiensb.service_desk.service.reference.WorkTypeService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/worktype")
@RequiredArgsConstructor
public class WorkTypeController {
    private final WorkTypeService service;

    @GetMapping
    public List<WorkType> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkType> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}