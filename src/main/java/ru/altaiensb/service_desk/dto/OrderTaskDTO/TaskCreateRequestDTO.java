package ru.altaiensb.service_desk.dto.OrderTaskDTO;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonFormat;

public record TaskCreateRequestDTO(
    // Обязательные поля
    @NotNull(message = "ID заявки обязателен") 
    @Positive(message = "ID заявки должен быть положительным") 
    Integer idOrder,

        /*
         * TODO: брать создателя из контекста
         * 
         * @NotNull(message = "ID создателя обязателен")
         * 
         * @Positive(message = "ID создателя должен быть положительным")
         * Integer idCreator,
         */

    // Опциональные поля
    @Positive(message = "ID родителя должен быть положительным") 
    Integer idOrderTaskParent,

    @Positive(message = "ID работы должен быть положительным")
    Integer idWork,

    @Positive(message = "ID исполнителя должен быть положительным") 
    Integer idExecutor,

    Boolean closeParentCheck,

    @Future(message = "Плановая дата должна быть в будущем") 
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant dateFinishPlan,

    String description
) {}
