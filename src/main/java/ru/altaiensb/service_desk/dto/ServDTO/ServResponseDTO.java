package ru.altaiensb.service_desk.dto.ServDTO;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import ru.altaiensb.service_desk.annotation.AllFieldsRequired;

@AllFieldsRequired
public record ServResponseDTO(
        Integer idService,
        String fullname,
        @Schema(nullable = true) String sname,
        @Schema(nullable = true) String description,
        @Schema(nullable = true) String developer,
        @Schema(nullable = true) LocalDate dateS,
        @Schema(nullable = true) LocalDate dateF,
        @Schema(nullable = true) Boolean priznakIs,
        Boolean isNeedApproval,
        Boolean isService,
        Short businessCritical,
        @Schema(nullable = true) String basisS,
        Integer serviceTypeId,
        String serviceTypeName,
        @Schema(nullable = true) Integer serviceStateId,
        @Schema(nullable = true) String serviceStateName,
        Integer expTypeId,
        @Schema(nullable = true) Integer serviceParentId
) {};