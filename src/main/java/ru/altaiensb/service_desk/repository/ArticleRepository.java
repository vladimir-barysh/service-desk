package ru.altaiensb.service_desk.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.altaiensb.service_desk.model.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {
    /*
     * Автоматически реализуются следующие методы:
     * save(entity)
     * findById(id)
     * findAll()
     * deleteById(id)
     * existsById(id)
     * count()
     */
}
