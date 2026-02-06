package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.WorkType;
import ru.altaiensb.service_desk.repository.WorkTypeRepository;

@Service
@RequiredArgsConstructor
public class WorkTypeService {

    private final WorkTypeRepository repo;

    public List<WorkType> getAll() {
        return repo.findAll();
    }

    public WorkType getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("WorkType not found with id=" + id));
    }
}
