package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Scale;
import ru.altaiensb.service_desk.repository.ScaleRepository;

@Service
@RequiredArgsConstructor
public class ScaleService {

    private final ScaleRepository repo;

    public List<Scale> getAll() {
        return repo.findAll();
    }

    public Scale getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Scale not found with id=" + id));
    }
}
