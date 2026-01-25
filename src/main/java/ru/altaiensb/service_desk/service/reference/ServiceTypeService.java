package ru.altaiensb.service_desk.service.reference;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.reference.ServiceTypeRepository;
import ru.altaiensb.service_desk.model.reference.ServiceType;

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
