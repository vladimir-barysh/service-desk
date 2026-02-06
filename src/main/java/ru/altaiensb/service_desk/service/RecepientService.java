package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Recepient;
import ru.altaiensb.service_desk.repository.RecepientRepository;

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
