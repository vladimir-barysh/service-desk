package ru.altaiensb.service_desk.service;

import lombok.RequiredArgsConstructor;
import ru.altaiensb.service_desk.dto.ArticleDTO;
import ru.altaiensb.service_desk.model.Article;
import ru.altaiensb.service_desk.repository.ArticleCategoryRepository;
import ru.altaiensb.service_desk.repository.ArticleRepository;
import ru.altaiensb.service_desk.repository.UserRepository;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepo;
    private final ArticleCategoryRepository articleCategoryRepo;
    private final UserRepository userRepo;

    public List<Article> getAll() {
        return articleRepo.findAll();
    }

    public Article getById(Integer id) {
        return articleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id=" + id));
    }

    public Article create(ArticleDTO dto) {
        Article article = new Article();

        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setArticleCategory(articleCategoryRepo.findById(dto.getIdArticleCategory())
                .orElseThrow(() -> new RuntimeException("Category not found")));
        article.setDateCreated(Instant.now());
        article.setUserCreator(userRepo.findById(1)
                .orElseThrow(() -> new RuntimeException("User not found with id=1")));

        article = articleRepo.save(article);
        return article;
    }

    public Article update(Integer id, ArticleDTO dto) {
        Article article = articleRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Article not found with id=" + id));
        article.setTitle(dto.getTitle());
        article.setContent(dto.getContent());
        article.setArticleCategory(articleCategoryRepo.findById(dto.getIdArticleCategory())
                .orElseThrow( () -> new RuntimeException("Article Category not found with id = " + id)));
        return articleRepo.save(article);
    }
}
