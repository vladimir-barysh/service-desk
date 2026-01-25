package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.ServiceCatItemRepository;
import ru.altaiensb.service_desk.model.core.ServiceCatItem;

@Service
@RequiredArgsConstructor
public class ServiceCatItemService {

    private final ServiceCatItemRepository repo;

    public List<ServiceCatItem> getAll() {
        return repo.findAll();
    }

    public ServiceCatItem getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceCatItem not found with id=" + id));
    }
}
