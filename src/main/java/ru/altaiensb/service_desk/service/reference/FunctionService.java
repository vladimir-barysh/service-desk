package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.reference.FunctionRepository;
import ru.altaiensb.service_desk.model.reference.Function;

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
