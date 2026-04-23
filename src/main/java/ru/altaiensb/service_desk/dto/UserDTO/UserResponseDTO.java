package ru.altaiensb.service_desk.dto.UserDTO;

import java.time.LocalDate;
import java.util.Set;

public record UserResponseDTO(
        Integer idItUser,
        String loginAd,
        String emailAd,
        String telAd,
        String fio1c,
        Integer podrId,                // идентификатор подразделения
        String dolzh1c,
        String tabNum1c,
        LocalDate dateCreate,
        LocalDate dateModern,
        Boolean isUser,
        LocalDate datePrin,
        LocalDate dateUvol,
        String agreementType,
        String fizLico,
        String state1c,
        Boolean interAd,
        Integer grade,
        Set<Integer> groupIds,         // идентификаторы групп пользователя
        Set<Integer> authorityIds      // идентификаторы ролей
) {}