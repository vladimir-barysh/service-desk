package ru.altaiensb.service_desk.controller.reference;

import ru.altaiensb.service_desk.model.reference.TaskState;
import ru.altaiensb.service_desk.service.reference.TaskStateService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/taskstate")
@RequiredArgsConstructor
public class TaskStateController {
    private final TaskStateService service;

    @GetMapping
    public List<TaskState> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskState> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}