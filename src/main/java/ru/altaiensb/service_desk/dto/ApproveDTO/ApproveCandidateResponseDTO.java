package ru.altaiensb.service_desk.dto.ApproveDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.altaiensb.service_desk.annotation.AllFieldsRequired;

@AllFieldsRequired
public record ApproveCandidateResponseDTO(
    Integer idUser,
    String fio1c,
    String userRoleName,

    @Schema(nullable = true)
    String podrName
) {}