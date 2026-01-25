package ru.altaiensb.service_desk.controller.reference;

import ru.altaiensb.service_desk.model.reference.UserRole;
import ru.altaiensb.service_desk.service.reference.UserRoleService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/userrole")
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService service;

    @GetMapping
    public List<UserRole> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserRole> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}