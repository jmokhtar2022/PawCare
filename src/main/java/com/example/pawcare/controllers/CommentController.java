package com.example.pawcare.controllers;



import com.example.pawcare.entities.Article;
import com.example.pawcare.entities.Comment;
import com.example.pawcare.repositories.IArticleRepository;
import com.example.pawcare.repositories.ICommentRepository;
import com.example.pawcare.services.article.ArticleServiceImp;
import com.example.pawcare.services.comment.CommentServiceImp;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comments")
public class CommentController {

    @Autowired
    private CommentServiceImp CommentService;
    @Autowired
    private ArticleServiceImp articleService;
    ICommentRepository commentRepository;

    @Autowired
    IArticleRepository articleRepository;
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("")
    public List<Comment> getAllComments() {
        return CommentService.retrieveAllComments();
    }

    @GetMapping("/{idComment}")
    public Comment getCommentById(@PathVariable Long idComment) {
        return CommentService.retrieveCommentById(idComment);
    }

    @PostMapping("/{articleId}/addComment")
    public ResponseEntity<Article> addCommentToArticle(@PathVariable Long articleId, @RequestBody Map<String, String> request) {
        String content = request.get("content");
        Comment comment = new Comment(content);
        Article article = CommentService.addCommentToArticle(articleId, comment);
        return ResponseEntity.ok(article);
    }
    @PutMapping("/updateComment/{idComment}")
    public Comment updateComment(@PathVariable Long idComment, @RequestBody Comment comment) {


        Comment existingComment = CommentService.retrieveCommentById(idComment);
        if (existingComment == null) {
            throw new OpenApiResourceNotFoundException("Comment not found");
        }


        comment.setIdComment(idComment);
        return CommentService.updateComment(comment);
    }

    @DeleteMapping("/deleteComment{idComment}")
    public void deleteComment(@PathVariable Long idComment) {


        Comment existingComment = CommentService.retrieveCommentById(idComment);
        if (existingComment == null) {
            throw new OpenApiResourceNotFoundException("Comment not found");
        }


        CommentService.deleteCommentById(idComment);
    }

}


