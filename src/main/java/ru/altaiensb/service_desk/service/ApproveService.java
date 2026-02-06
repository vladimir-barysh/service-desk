package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.Approve;
import ru.altaiensb.service_desk.repository.ApproveRepository;

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
