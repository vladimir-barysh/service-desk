package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.reference.PodrRepository;
import ru.altaiensb.service_desk.model.reference.Podr;

@Service
@RequiredArgsConstructor
public class PodrService {

    private final PodrRepository repo;

    public List<Podr> getAll() {
        return repo.findAll();
    }

    public Podr getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Podr not found with id=" + id));
    }
}
