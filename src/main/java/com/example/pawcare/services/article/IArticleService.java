package com.example.pawcare.services.article;

import com.example.pawcare.entities.Article;
import com.example.pawcare.entities.Comment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface IArticleService {
    List<Article> retrieveAllArticles();
    Article addArticle(Article article);

    Article addCommentToArticle(Long articleId, Comment comment);

    Article updateArticle(Article article);
    Article retrieveArticleById(Long id);
    void deleteArticleById(Long id);

    ResponseEntity<Article> incrementLikes(@PathVariable Long id);
}

