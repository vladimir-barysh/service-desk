package ru.altaiensb.service_desk.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;

public record ApproveResponseDTO(
        Integer idApprove,
        Integer idOrder,
        String name,
        Integer idUserCreator,
        Boolean flagApproved,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") Instant dateCreated,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") Instant datePlan,
        Short state,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") Instant dateFact,
        String taskText
) {}