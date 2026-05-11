package ru.altaiensb.service_desk.dto.OrderDTO;
import java.time.Instant;

public record OrderBindingDTO(
        Integer id,
        String name,
        Instant dateCreated
) {}