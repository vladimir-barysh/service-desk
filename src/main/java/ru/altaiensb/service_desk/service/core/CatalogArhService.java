package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.CatalogArh;
import ru.altaiensb.service_desk.repository.core.CatalogArhRepository;

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
