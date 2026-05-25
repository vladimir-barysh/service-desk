package ru.altaiensb.service_desk.dto.ApproveUserDTO;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.altaiensb.service_desk.annotation.AllFieldsRequired;
import java.time.Instant;

@AllFieldsRequired
public record ApproveUserResponseDTO(
    Integer idApproveUser,
    Integer idApprove,
    Integer userId,
    String userFio,
    Integer userRoleId,
    String userRoleName,
    Integer idApproveUserState,
    Boolean flagIgnored,

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant datePlan,

    // Могут быть null
    @Schema(nullable = true)
    String  resultText,

    @Schema(nullable = true)
    Integer idApproveUserParent,

    @Schema(nullable = true)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC") 
    Instant dateFact,

    @Schema(nullable = true)
    String  taskText
) {}