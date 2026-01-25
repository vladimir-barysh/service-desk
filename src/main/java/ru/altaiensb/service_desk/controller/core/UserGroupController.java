package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.UserGroup;
import ru.altaiensb.service_desk.service.core.UserGroupService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usergroup")
@RequiredArgsConstructor
public class UserGroupController {
    private final UserGroupService service;

    @GetMapping
    public List<UserGroup> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserGroup> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
