package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.FactLocation;
import ru.altaiensb.service_desk.repository.reference.FactLocationRepository;

@Service
@RequiredArgsConstructor
public class FactLocationService {

    private final FactLocationRepository repo;

    public List<FactLocation> getAll() {
        return repo.findAll();
    }

    public FactLocation getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("FactLocation not found with id=" + id));
    }
}
