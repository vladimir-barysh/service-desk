package ru.altaiensb.service_desk.dto.OrderTaskDTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.altaiensb.service_desk.annotation.AllFieldsRequired;
import java.time.Instant;

@AllFieldsRequired
public record TaskResponseDTO(
        Integer idOrderTask,

        @Schema(nullable = true) @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") Instant dateFinishPlan,

        @Schema(nullable = true) @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") Instant dateFinishFact,

        String description,

        Boolean closeParentCheck,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") Instant dateCreated,

        @Schema(nullable = true) String resultText,

        // Связные талицы с частью используемых данных
        Integer orderId,
        Integer orderNomer,
        String orderName,
        Integer orderTypeId,
        String orderTypeName,
        Integer orderServiceId,
        String orderServiceFullname,
        Integer orderCatItemId,
        String orderCatItemName,
        @Schema(nullable = true) Integer orderTaskParentId,
        @Schema(nullable = true) Integer workId,
        @Schema(nullable = true) Integer executorId,
        @Schema(nullable = true) String executorFio,
        Integer taskStateId,
        String taskStateName,
        Integer creatorId,
        String creatorFio) {
}
