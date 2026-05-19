package ru.altaiensb.service_desk.dto.ApproveDTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record ApproveCreateRequestDTO(
        // Обязательные поля
        @NotNull(message = "ID заявки обязателен")
        @Positive(message = "ID заявки должен быть положительным числом")
        Integer idOrder,

        // НЕ обязательные поля
        List<@Positive(message = "ID пользователя должен быть положительным") Integer> userIds
) {}