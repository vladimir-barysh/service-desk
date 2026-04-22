package ru.altaiensb.service_desk.dto.ApproveUserDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.Instant;

public record ApproveUserResponseDTO(
    Integer idApproveUser,
    Integer idApprove,
    Integer idUser,
    Integer idUserRole,
    Short   state,
    String  resultText,
    Integer idApproveUserParent,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") Instant datePlan,
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") Instant dateFact,
    String  taskText
) {}