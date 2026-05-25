package ru.altaiensb.service_desk.dto.ApproveUserDTO;

import jakarta.validation.constraints.NotNull;

public record ApproveUserUpdateIgnoredRequestDTO(
    @NotNull Boolean ignored
) {}