package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Operation;
import ru.altaiensb.service_desk.repository.reference.OperationRepository;

@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository repo;

    public List<Operation> getAll() {
        return repo.findAll();
    }

    public Operation getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Operation not found with id=" + id));
    }
}
