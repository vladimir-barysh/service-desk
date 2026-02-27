package ru.altaiensb.service_desk.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "it_article_category", schema = "sd_core")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArticleCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article_category")
    private Integer idArticleCategory;

    @Column(name = "name")
    private String name;

}
