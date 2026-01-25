package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.Group;
import ru.altaiensb.service_desk.service.core.GroupService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group")
@RequiredArgsConstructor
public class GroupController {
    private final GroupService service;

    @GetMapping
    public List<Group> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Group> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
