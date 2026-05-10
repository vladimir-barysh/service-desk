package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Set;

import ru.altaiensb.service_desk.model.FactLocation;
import ru.altaiensb.service_desk.model.Podr;
import ru.altaiensb.service_desk.repository.PodrRepository;
import ru.altaiensb.service_desk.dto.PodrDTO.PodrResponseDTO;
import ru.altaiensb.service_desk.exception.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class PodrService {
    private final PodrRepository repo;

    private PodrResponseDTO toResponse(Podr podr){
        Set<Integer> locationIds = podr.getFactLocations().stream()
        .map(FactLocation::getIdFactLocation)
        .collect(Collectors.toSet());

        return new PodrResponseDTO(
            podr.getIdPodr(),
            podr.getName(),
            podr.getPodrParent() != null ? podr.getPodrParent().getIdPodr() : null,
            podr.getId1c(),
            podr.getIsDeleted(),
            podr.getPor(),
            locationIds
        );
    }

    @Transactional(readOnly = true)
    public List<PodrResponseDTO> getAll() {
        return repo.findAll().stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PodrResponseDTO getById(Integer id) {
        Podr podr = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Podr", id));
        return toResponse(podr);
    }
}
