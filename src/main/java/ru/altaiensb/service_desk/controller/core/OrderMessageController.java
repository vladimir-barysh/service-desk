package ru.altaiensb.service_desk.controller.core;

import ru.altaiensb.service_desk.model.core.OrderMessage;
import ru.altaiensb.service_desk.service.core.OrderMessageService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ordermessage")
@RequiredArgsConstructor
public class OrderMessageController {
    private final OrderMessageService service;

    @GetMapping
    public List<OrderMessage> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderMessage> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
