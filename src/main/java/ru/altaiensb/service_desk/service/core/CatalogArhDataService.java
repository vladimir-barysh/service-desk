package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.model.CatalogArhData;
import ru.altaiensb.service_desk.repository.core.CatalogArhDataRepository;

@Service
@RequiredArgsConstructor
public class CatalogArhDataService {

    private final CatalogArhDataRepository repo;

    public List<CatalogArhData> getAll() {
        return repo.findAll();
    }

    public CatalogArhData getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("CatalogArhData not found with id=" + id));
    }
}
