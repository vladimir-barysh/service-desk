package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Work;
import ru.altaiensb.service_desk.repository.WorkRepository;

@Service
@RequiredArgsConstructor
public class WorkService {

    private final WorkRepository repo;

    public List<Work> getAll() {
        return repo.findAll();
    }

    public Work getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Work not found with id=" + id));
    }
}
