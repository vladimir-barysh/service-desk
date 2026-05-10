package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.model.Authority;
import ru.altaiensb.service_desk.repository.AuthorityRepository;
import ru.altaiensb.service_desk.dto.AuthorityDTO.AuthorityResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class AuthorityService {
    private final AuthorityRepository repo;

    private AuthorityResponseDTO toResponse(Authority authority){
        return new AuthorityResponseDTO(
            authority.getIdAuthority(),
            authority.getAuthority(),
            authority.getDescription()
        );
    }

    @Transactional(readOnly = true)
    public List<AuthorityResponseDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public AuthorityResponseDTO getById(Integer id) {
        Authority authority = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Authority", id));
        return toResponse(authority);
    }
}
