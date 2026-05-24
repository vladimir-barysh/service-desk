package ru.altaiensb.service_desk.dto.ApproveUserDTO;

import jakarta.validation.constraints.NotNull;

public record ApproveUserUpdateRequestDTO(
    @NotNull Short state,
    String resultText
) {}