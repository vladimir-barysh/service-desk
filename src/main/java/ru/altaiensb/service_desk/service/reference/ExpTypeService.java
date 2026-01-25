package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.reference.ExpTypeRepository;
import ru.altaiensb.service_desk.model.reference.ExpType;

@Service
@RequiredArgsConstructor
public class ExpTypeService {

    private final ExpTypeRepository repo;

    public List<ExpType> getAll() {
        return repo.findAll();
    }

    public ExpType getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("ExpType not found with id=" + id));
    }
}
