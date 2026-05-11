package ru.altaiensb.service_desk.dto.OrderDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public record OrderCreateRequestDTO(
    // Обязательные поля для всех типов заявок
    @NotNull(message = "ID сервиса обязателен")
    @Positive(message = "ID сервиса должен быть положительным")
    Integer idService,

    @NotNull(message = "ID услуги обязателен")
    @Positive(message = "ID услуги каталога должен быть положительным")
    Integer idCatItem,

    @NotNull(message = "ID инициатора обязателен")
    @Positive(message = "ID инициатора должен быть положительным")
    Integer idInitiator,

    @NotNull(message = "ID типа заявки обязателен")
    @Positive(message = "ID типа заявки должен быть положительным числом")
    Integer idOrderType,

    @NotBlank(message = "Описание в заявке не может быть пустым")
    String description,

    // Опциональные поля (могут быть у разных типов)
    String comment,

    @Positive(message = "ID источника заявки должен быть положительным")
    Integer idOrderSource,

    @Future(message = "Плановая дата должна быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    Instant dateFinishPlan,

    // Поле только для ЗНТ, которое в нём обязательно
    @Future(message = "Дата возврата техники должна быть в будущем")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    Instant dateTechReturn
) {}