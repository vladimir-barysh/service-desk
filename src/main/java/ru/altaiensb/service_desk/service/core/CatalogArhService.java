package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.CatalogArhRepository;
import ru.altaiensb.service_desk.model.core.CatalogArh;

@Service
@RequiredArgsConstructor
public class CatalogArhService {

    private final CatalogArhRepository repo;

    public List<CatalogArh> getAll() {
        return repo.findAll();
    }

    public CatalogArh getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("CatalogArh not found with id=" + id));
    }
}
