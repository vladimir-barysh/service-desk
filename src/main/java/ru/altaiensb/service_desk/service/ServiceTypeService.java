package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.ServiceType;
import ru.altaiensb.service_desk.repository.ServiceTypeRepository;

@Service
@RequiredArgsConstructor
public class ServiceTypeService {

    private final ServiceTypeRepository repo;

    public List<ServiceType> getAll() {
        return repo.findAll();
    }

    public ServiceType getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceType not found with id=" + id));
    }
}
