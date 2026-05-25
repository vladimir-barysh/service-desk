package ru.altaiensb.service_desk.dto.ApproveUserDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ApproveUserUpdateRequestDTO(
    @NotNull(message = "ID статуса согласованта обязателен")
    @Positive(message = "ID статуса согласованта должен быть положительным числом")
    Integer idApproveUserState,

    String resultText
) {}