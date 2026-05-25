package ru.altaiensb.service_desk.controller;

import ru.altaiensb.service_desk.service.ApproveUserService;
import ru.altaiensb.service_desk.dto.ApproveUserDTO.ApproveUserUpdateRequestDTO;
import ru.altaiensb.service_desk.dto.ApproveUserDTO.ApproveUserResponseDTO;

import lombok.RequiredArgsConstructor;

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

    @PatchMapping("/{approveId}/self")
    public ResponseEntity<ApproveUserResponseDTO> updateSelf(
            @PathVariable Integer approveId,
            @RequestBody @Valid ApproveUserUpdateRequestDTO dto) {
        return ResponseEntity.ok(service.updateSelf(approveId, dto.idApproveUserState(), dto.resultText()));
    }
}