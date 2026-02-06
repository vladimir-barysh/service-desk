package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.ServiceCatItem;
import ru.altaiensb.service_desk.repository.ServiceCatItemRepository;

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
