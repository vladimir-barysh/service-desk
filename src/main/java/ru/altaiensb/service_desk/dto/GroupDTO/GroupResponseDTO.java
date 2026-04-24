package ru.altaiensb.service_desk.dto.GroupDTO;

public record GroupResponseDTO(
        Integer idGroup,
        String  name,
        String  name1cDoc,
        String  description,
        Integer idResponsibleUser         // идентификатор руководителя группы
) {}