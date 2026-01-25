package ru.altaiensb.service_desk.service.core;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import ru.altaiensb.service_desk.repository.core.DocumentRepository;
import ru.altaiensb.service_desk.model.core.Document;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository repo;

    public List<Document> getAll() {
        return repo.findAll();
    }

    public Document getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Document not found with id=" + id));
    }
}
