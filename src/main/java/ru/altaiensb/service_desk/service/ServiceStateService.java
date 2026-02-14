package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.ServiceState;
import ru.altaiensb.service_desk.repository.ServiceStateRepository;

@Service
@RequiredArgsConstructor
public class ServiceStateService {

    private final ServiceStateRepository repo;

    public List<ServiceState> getAll() {
        return repo.findAll();
    }

    public ServiceState getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceState not found with id=" + id));
    }
}
