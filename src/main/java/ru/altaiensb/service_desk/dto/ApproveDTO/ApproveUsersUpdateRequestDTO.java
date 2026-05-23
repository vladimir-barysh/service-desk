package ru.altaiensb.service_desk.dto.ApproveDTO;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record ApproveUsersUpdateRequestDTO(
    @NotNull List<Integer> userIds
) {}