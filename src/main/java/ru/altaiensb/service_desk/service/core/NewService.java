package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.NewRepository;
import ru.altaiensb.service_desk.model.core.New;

@Service
@RequiredArgsConstructor
public class NewService {

    private final NewRepository repo;

    public List<New> getAll() {
        return repo.findAll();
    }

    public New getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("New not found with id=" + id));
    }
}
