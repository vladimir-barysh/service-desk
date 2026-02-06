package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.PodrFactLocation;
import ru.altaiensb.service_desk.repository.PodrFactLocationRepository;

@Service
@RequiredArgsConstructor
public class PodrFactLocationService {

    private final PodrFactLocationRepository repo;

    public List<PodrFactLocation> getAll() {
        return repo.findAll();
    }

    public PodrFactLocation getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("PodrFactLocation not found with id=" + id));
    }
}
