package com.example.pawcare.services.comment;

import com.example.pawcare.entities.Article;
import com.example.pawcare.entities.Comment;

import java.util.List;

public interface ICommentService {
    List<Comment> retrieveAllComments();

    //Comment addComment(Comment comment);

    List<Comment> getCommentsForArticle(Long articleId);

     Article addCommentToArticle(Long articleId, Comment comment);



    Comment updateComment(Comment comment);

    Comment retrieveCommentById(Long idComment);

    List<Comment> getCommentsByArticleId(Long articleId);

    void deleteCommentById(Long idComment);
}
