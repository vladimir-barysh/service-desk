package ru.altaiensb.service_desk.dto.ApproveUserDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Max;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;

public record ApproveUserCreateRequestDTO(
    // Обязательные поля
    @NotNull(message = "ID согласования обязателен")
    @Positive(message = "ID согласования должен быть положительным числом")
    Integer idApprove,

    @NotNull(message = "ID пользователя обязателен")
    @Positive(message = "ID пользователя должен быть положительным числом")
    Integer idUser,

    // НЕ обязательные поля
    @Positive(message = "ID роли должен быть положительным числом")
    Integer idUserRole,

    @Min(value = 0, message = "State может быть 0, 1 или 2")
    @Max(value = 2, message = "State может быть 0, 1 или 2")
    Short state,

    @Positive(message = "ID родительской записи должен быть положительным числом")
    Integer idApproveUserParent,

    @Future(message = "Плановая дата должна быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    Instant datePlan,

    String taskText
) {}
