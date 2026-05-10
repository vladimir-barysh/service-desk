package ru.altaiensb.service_desk.dto.AuthorityDTO;

public record AuthorityResponseDTO (
    Integer idAuthority,
    String authority,
    String description
) {}
