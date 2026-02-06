package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Sklad;
import ru.altaiensb.service_desk.repository.reference.SkladRepository;

@Service
@RequiredArgsConstructor
public class SkladService {

    private final SkladRepository repo;

    public List<Sklad> getAll() {
        return repo.findAll();
    }

    public Sklad getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sklad not found with id=" + id));
    }
}
