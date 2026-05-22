package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveCreateRequestDTO;
import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveResponseDTO;
import ru.altaiensb.service_desk.dto.ApproveDTO.ApproveCandidateResponseDTO;
import ru.altaiensb.service_desk.service.ApproveService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/approve")
@RequiredArgsConstructor
public class ApproveController {
    private final ApproveService service;

    @GetMapping
    public ResponseEntity<List<ApproveResponseDTO>> getByOrderId( @RequestParam Integer orderId) {
        return ResponseEntity.ok(service.getByOrderId(orderId));
    }

    @GetMapping("/candidate")
    public ResponseEntity<List<ApproveCandidateResponseDTO>> getApproveCandidate(@RequestParam Integer orderId) {
        return ResponseEntity.ok(service.getCandidatesForOrder(orderId));
    }

    @PatchMapping("/{id}/start")
    public ResponseEntity<ApproveResponseDTO> startProcess(@PathVariable Integer id) {
        return ResponseEntity.ok(service.startProcess(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApproveResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/refresh/{orderId}")
    public ResponseEntity<List<ApproveResponseDTO>> refreshByOrder(@PathVariable Integer orderId) {
        return ResponseEntity.ok(service.refreshByOrder(orderId));
    }
    
    @PostMapping
    public ResponseEntity<ApproveResponseDTO> create(@Valid @RequestBody ApproveCreateRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(dto));
    }
}
