package ru.altaiensb.service_desk.dto.ServDTO;

import java.time.LocalDate;

public record ServResponseDTO(
        Integer idService,
        String fullname,
        String sname,
        String description,
        String developer,
        LocalDate dateS,
        LocalDate dateF,
        Boolean priznakIs,
        Integer serviceTypeId,
        Integer serviceStateId,
        Integer expTypeId,
        Integer serviceParentId,
        Boolean isNeedApproval,
        Boolean isService,
        Short businessCritical,
        String basisS
) {}