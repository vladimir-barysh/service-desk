package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import ru.altaiensb.service_desk.model.ArticleCategory;
import ru.altaiensb.service_desk.repository.ArticleCategoryRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleCategoryService {

    private final ArticleCategoryRepository repo;

    public List<ArticleCategory> getAll() {
        return repo.findAll();
    }

    public ArticleCategory getById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("ArticleCategory not found with id=" + id));
    }
}
