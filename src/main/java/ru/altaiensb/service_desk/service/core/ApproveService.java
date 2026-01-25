package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.ApproveRepository;
import ru.altaiensb.service_desk.model.core.Approve;

@Service
@RequiredArgsConstructor
public class ApproveService {

    private final ApproveRepository repo;

    public List<Approve> getAll() {
        return repo.findAll();
    }

    public Approve getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Approve not found with id=" + id));
    }
}
