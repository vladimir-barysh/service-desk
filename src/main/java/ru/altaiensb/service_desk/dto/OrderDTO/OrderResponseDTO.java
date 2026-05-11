package ru.altaiensb.service_desk.dto.OrderDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;

public record OrderResponseDTO(
    Integer idOrder,
    Integer nomer,
    String name,
    String description,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant dateCreated,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant dateFinishPlan,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant dateFinishFact,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant datePostpone,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    Instant dateTechReturn,
    String comment,
    String resultText,

    // Связные талицы с частью используемых данных
    Integer orderParentId,
    Integer initiatorId,
    Integer creatorId,
    Integer dispatcherId, String dispatcherFio,
    Integer executorId, String executorFio,
    Integer orderTypeId, String orderTypeName,
    Integer catalogItemId, String catalogItemName,
    Integer serviceId, String serviceFullname,
    Integer orderStateId, String orderStateName,
    Integer orderPriorityId, String orderPriorityName,
    Integer orderSourceId, String orderSourceName
) {}