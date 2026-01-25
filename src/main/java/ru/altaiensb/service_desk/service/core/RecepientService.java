package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.RecepientRepository;
import ru.altaiensb.service_desk.model.core.Recepient;

@Service
@RequiredArgsConstructor
public class RecepientService {

    private final RecepientRepository repo;

    public List<Recepient> getAll() {
        return repo.findAll();
    }

    public Recepient getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Recepient not found with id=" + id));
    }
}
