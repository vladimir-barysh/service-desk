package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.service.ApproveUserService;
import ru.altaiensb.service_desk.dto.ApproveUserDTO.ApproveUserCreateRequestDTO;
import ru.altaiensb.service_desk.dto.ApproveUserDTO.ApproveUserResponseDTO;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

import java.util.List;


@RestController
@RequestMapping("/api/approveuser")
@RequiredArgsConstructor
public class ApproveUserController {
    private final ApproveUserService service;

    @GetMapping
    public ResponseEntity<List<ApproveUserResponseDTO>> getByApproveId( @RequestParam Integer approveId) {
        return ResponseEntity.ok(service.getByApproveId(approveId));
    }

    @GetMapping("/by-order")
    public ResponseEntity<List<ApproveUserResponseDTO>> getByOrderId(@RequestParam Integer orderId) {
        return ResponseEntity.ok(service.getByOrderId(orderId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApproveUserResponseDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }

/*     @PostMapping
    public ResponseEntity<ApproveUserResponseDTO> create(@Valid @RequestBody ApproveUserCreateRequestDTO dto) {
        ApproveUserResponseDTO created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    } */
}