package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.model.Order;
import ru.altaiensb.service_desk.service.OrderService;
import ru.altaiensb.service_desk.dto.OrderDTO;
import ru.altaiensb.service_desk.dto.OrderStatusUpdateDTO;
import ru.altaiensb.service_desk.model.OrderState;

import lombok.RequiredArgsConstructor;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService service;

    @GetMapping
    public List<Order> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")  
    public ResponseEntity<Order> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<Order> create (
            @RequestPart("dto") OrderDTO dto,
            @RequestPart(value = "files", required = false) List<MultipartFile> files
    ) {
        try {
            Order createdOrder = service.create(dto, files, 1);
            return ResponseEntity.ok(createdOrder);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Order> update(@PathVariable("id") Integer id, @RequestBody OrderDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Order> updateStatus(@PathVariable Integer id, @RequestBody OrderStatusUpdateDTO statusDto) {
        return ResponseEntity.ok(service.updateStatus(id, statusDto.getIdOrderState()));
    }
}
