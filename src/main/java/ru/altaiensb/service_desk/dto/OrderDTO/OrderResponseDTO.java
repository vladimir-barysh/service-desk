package ru.altaiensb.service_desk.dto.OrderDTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.altaiensb.service_desk.annotation.AllFieldsRequired;
import java.time.Instant;

@AllFieldsRequired
public record OrderResponseDTO(
    Integer idOrder,
    Integer nomer,
    String name,
    String description,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant dateCreated,

    @Schema(nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant dateFinishPlan,

    @Schema(nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant dateFinishFact,

    @Schema(nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant datePostpone,
    
    @Schema(nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    Instant dateTechReturn,

    @Schema(nullable = true)
    String comment,

    @Schema(nullable = true)
    String resultText,

    // Связные таблицы с частью используемых данных
    @Schema(nullable = true) Integer orderParentId,
    Integer initiatorId,
    Integer creatorId,
    @Schema(nullable = true) Integer dispatcherId,  
    @Schema(nullable = true) String dispatcherFio,
    @Schema(nullable = true) Integer executorId, 
    @Schema(nullable = true) String executorFio,
    Integer orderTypeId, 
    String orderTypeName,
    Integer catalogItemId, 
    String catalogItemName,
    Integer serviceId, 
    String serviceFullname,
    Integer orderStateId, 
    String orderStateName,
    Integer orderPriorityId, 
    String orderPriorityName,
    Integer orderSourceId, 
    String orderSourceName
) {}