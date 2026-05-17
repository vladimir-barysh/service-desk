package ru.altaiensb.service_desk.dto.ApproveDTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.altaiensb.service_desk.annotation.AllFieldsRequired;
import java.time.Instant;

@AllFieldsRequired
public record ApproveResponseDTO(
        Integer idApprove,
        Integer idOrder,
        String name,
        Integer idUserCreator,
        Boolean flagApproved,
        Integer idApproveState,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
        Instant dateCreated,

        // Могут быть null
        @Schema(nullable = true)
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
        Instant datePlan,

        @Schema(nullable = true)
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
        Instant dateFact,

        @Schema(nullable = true)
        String taskText
) {}