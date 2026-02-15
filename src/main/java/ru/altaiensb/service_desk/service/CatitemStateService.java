package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.CatitemState;
import ru.altaiensb.service_desk.repository.CatitemStateRepository;

@Service
@RequiredArgsConstructor
public class CatitemStateService {

    private final CatitemStateRepository repo;

    public List<CatitemState> getAll() {
        return repo.findAll();
    }

    public CatitemState getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("CatitemState not found with id=" + id));
    }
}