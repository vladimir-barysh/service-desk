package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.ServiceRepository;
import ru.altaiensb.service_desk.model.core.Serv;

@Service
@RequiredArgsConstructor
public class ServiceService {

    private final ServiceRepository repo;

    public List<Serv> getAll() {
        return repo.findAll();
    }

    public Serv getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Service not found with id=" + id));
    }
}
