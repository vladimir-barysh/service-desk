package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.CatalogItemRepository;
import ru.altaiensb.service_desk.model.core.CatalogItem;

@Service
@RequiredArgsConstructor
public class CatalogItemService {

    private final CatalogItemRepository repo;

    public List<CatalogItem> getAll() {
        return repo.findAll();
    }

    public CatalogItem getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("CatalogItem not found with id=" + id));
    }
}
