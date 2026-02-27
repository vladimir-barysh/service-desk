package ru.altaiensb.service_desk.dto;

import java.time.Instant;

import lombok.Data;

@Data
public class ArticleDTO {
    private String title;
    private String content;
    private Instant dateCreated;
    private Integer idArticleCategory;
    //private Integer idUserCreator; потом добавить, когда появятся пользователи
}