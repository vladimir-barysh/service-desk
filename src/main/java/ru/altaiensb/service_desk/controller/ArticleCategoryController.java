package ru.altaiensb.service_desk.controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.altaiensb.service_desk.service.ArticleCategoryService;
import ru.altaiensb.service_desk.model.ArticleCategory;

import java.util.List;

@RestController
@RequestMapping("/api/articlecategory")
@RequiredArgsConstructor
public class ArticleCategoryController {
    private final ArticleCategoryService service;

    @GetMapping
    public List<ArticleCategory> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticleCategory> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(service.getById(id));
    }
}
