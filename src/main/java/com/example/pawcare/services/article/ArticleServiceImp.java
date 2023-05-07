package com.example.pawcare.services.article;

import com.example.pawcare.entities.Article;
import com.example.pawcare.entities.Comment;
import com.example.pawcare.repositories.IArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;


@Service
public class ArticleServiceImp implements IArticleService {

    @Autowired
    IArticleRepository articleRepository;

    @Override
    public List<Article> retrieveAllArticles() {
        return articleRepository.findAll();
    }

    @Override
    public Article addArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article addCommentToArticle(Long articleId, Comment comment) {
        Article article = articleRepository.findById(articleId).get();
        comment.setArticle(article);
        article.getComments().add(comment);
        article.setNbcomments(article.getNbcomments() + 1); // update nbcomments field
        return updateArticle(article);
    }

    @Override
    public Article updateArticle(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article retrieveArticleById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteArticleById(Long id) {articleRepository.deleteById(id);
    }

    @PersistenceContext
    private EntityManager entityManager;
    public int getArticleCount() {
        Query query = entityManager.createQuery("SELECT COUNT(a) FROM Article a");
        return ((Number) query.getSingleResult()).intValue();
    }
@Override
    public ResponseEntity<Article> incrementLikes(@PathVariable Long id) {
    Optional<Article> articleOptional = Optional.ofNullable(articleRepository.findById(id).get());
    if (articleOptional.isPresent()) {
        Article article = articleOptional.get();
        article.setNblike(article.getNblike() + 1);
        return ResponseEntity.ok(articleRepository.save(article));
    } else {
        return ResponseEntity.notFound().build();
    }
}
  /*  public User GetUserFromSession()
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                String username = ((UserDetails) principal).getUsername();
                Optional<User> userOptional = userRepository.findByUsername(username);
                assert userOptional.orElse(null) != null;
                return userOptional.orElse(null);
            } else {
                String username = principal.toString();
                Optional<User> userOptional = userRepository.findByUsername(username);
                assert userOptional.orElse(null) != null;
                return userOptional.orElse(null);
            }
        }
        return null;
    }*/
}
