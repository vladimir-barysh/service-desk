package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import ru.altaiensb.service_desk.dto.FactLocationDTO.FactLocationResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;
import ru.altaiensb.service_desk.model.FactLocation;
import ru.altaiensb.service_desk.repository.FactLocationRepository;

@Service
@RequiredArgsConstructor
public class FactLocationService {

    private final FactLocationRepository repo;

    private FactLocationResponseDTO toResponse(FactLocation location) {
        return new FactLocationResponseDTO(
                location.getIdFactLocation(),
                location.getName()
        );
    }

    @Transactional(readOnly = true)
    public List<FactLocationResponseDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public FactLocationResponseDTO getById(Integer id) {
        FactLocation location = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("FactLocation", id));
        return toResponse(location);
    }
}