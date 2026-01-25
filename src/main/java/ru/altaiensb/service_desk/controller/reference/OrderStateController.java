package ru.altaiensb.service_desk.controller.reference;

import ru.altaiensb.service_desk.model.reference.OrderState;
import ru.altaiensb.service_desk.service.reference.OrderStateService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/orderstate")
@RequiredArgsConstructor
public class OrderStateController {
    private final OrderStateService service;

    @GetMapping
    public List<OrderState> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderState> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}