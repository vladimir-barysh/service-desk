package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.ApproveUser;
import ru.altaiensb.service_desk.service.core.ApproveUserService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/approveuser")
@RequiredArgsConstructor
public class ApproveUserController {
    private final ApproveUserService service;

    @GetMapping
    public List<ApproveUser> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApproveUser> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
