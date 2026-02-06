package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Function;
import ru.altaiensb.service_desk.repository.FunctionRepository;

@Service
@RequiredArgsConstructor
public class FunctionService {

    private final FunctionRepository repo;

    public List<Function> getAll() {
        return repo.findAll();
    }

    public Function getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Function not found with id=" + id));
    }
}
