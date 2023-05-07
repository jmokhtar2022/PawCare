package com.example.pawcare.services.comment;

import com.example.pawcare.entities.Article;
import com.example.pawcare.entities.Comment;
import com.example.pawcare.repositories.IArticleRepository;
import com.example.pawcare.repositories.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;


@Service
public class CommentServiceImp implements ICommentService {
    @Autowired
     IArticleRepository articleRepository;


    @Autowired
    ICommentRepository commentRepository;

    @Override
    public List<Comment> retrieveAllComments() {
        return commentRepository.findAll();
    }
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Comment> getCommentsForArticle(Long id) {
        TypedQuery<Comment> query = entityManager.createQuery(
                "SELECT c FROM Comment c WHERE c.article.id = :id", Comment.class);
        query.setParameter("id", id);
        return query.getResultList();
    }
    @Override
    public Article addCommentToArticle(Long articleId, Comment comment) {
        Article article = articleRepository.findById(articleId).get();

        article.getComments().add(comment);
        articleRepository.save(article);

        return article;
    }

    @Override
    public Comment updateComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment retrieveCommentById(Long idComment) {
        return commentRepository.findById(idComment).orElse(null);
    }

    @Override
    public List<Comment> getCommentsByArticleId(Long id) {return commentRepository.findByArticleId(id);
    }

    @Override
    public void deleteCommentById(Long idComment) {commentRepository.deleteById(idComment);
    }


    }


