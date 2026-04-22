package ru.altaiensb.service_desk.dto.ApproveDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;
import com.fasterxml.jackson.annotation.JsonFormat;

public record ApproveCreateRequestDTO(
        // Обязательные поля
        @NotNull(message = "ID заявки обязателен")
        @Positive(message = "ID заявки должен быть положительным числом")
        Integer idOrder,

        @NotBlank(message = "Название согласования не может быть пустым")
        String name,

        @NotNull(message = "ID создателя обязателен")
        @Positive(message = "ID пользователя должен быть положительным")
        Integer idUserCreator,

        @NotNull(message = "ID статуса согласования обязателен")
        @Positive(message = "ID статуса согласования должен быть положительным")
        Integer idApproveState,

        // НЕ обязательные поля
        @Future(message = "Плановая дата должна быть в будущем")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
        Instant datePlan,

        String taskText
) {}