package com.example.pawcare.controllers;


import com.example.pawcare.entities.Article;
import com.example.pawcare.entities.Comment;
import com.example.pawcare.repositories.IArticleRepository;
import com.example.pawcare.services.article.ArticleServiceImp;
import com.example.pawcare.services.comment.CommentServiceImp;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/articles")
public class ArticleController {

        @Autowired
        private ArticleServiceImp articleService;
        @Autowired
        private CommentServiceImp commentService;
        private IArticleRepository articleRepository;



    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("")
    public List<Article> getAllArticles() {
        return articleService.retrieveAllArticles();
    }

    @GetMapping("/{id}/comments")
    public List<Comment> getCommentsForArticle(@PathVariable Long id) {
        return commentService.getCommentsForArticle(id);
    }

    @PostMapping("/{articleId}/addComment")
    public ResponseEntity<Article> addCommentToArticle(@PathVariable Long articleId, @RequestBody Map<String, String> request) {
        String content = request.get("content");
        Comment comment = new Comment(content);
        Article article = articleService.addCommentToArticle(articleId, comment);
        return ResponseEntity.ok(article);
    }
    @GetMapping("/{id}")
    public Article getArticleById(@PathVariable Long id) {

        return articleService.retrieveArticleById(id);
    }

    @PostMapping("/addArticle")
    public Article createArticle(@ModelAttribute Article article, @RequestParam("imageFile") MultipartFile imageFile /*Authentication authentication*/) throws IOException {


        if (!imageFile.isEmpty()) {
            String fileName = imageFile.getOriginalFilename();
            Path filePath = Paths.get("C:/Users/Bruce/Desktop/Pi/img", fileName);
            Files.write(filePath, imageFile.getBytes());
            article.setMedia(filePath.toString());
        }
        return articleService.addArticle(article);
    }



    @PutMapping("/updateArticle/{id}")
    public Article updateArticle(@PathVariable Long id, @ModelAttribute Article article, @RequestParam(value= "imageFile", required= false) MultipartFile imageFile)throws  IOException {
       Article existingArticle = articleService.retrieveArticleById(id);
       if (existingArticle== null){
           throw new OpenApiResourceNotFoundException("Not found");

       }


       if (imageFile!= null && !imageFile.isEmpty()) {
           String fileName = imageFile.getOriginalFilename();
           Path filePath = Paths.get("C:/Users/Bruce/Desktop/Pi/img", fileName);
           Files.write(filePath, imageFile.getBytes());
           article.setMedia(filePath.toString());
       }else  {
           article.setMedia(existingArticle.getMedia());
       }
       article.setId(id);
       return articleService.updateArticle(article);
    }

    @DeleteMapping("/deleteArticle/{id}")
    public void deleteArticle(@PathVariable Long id ) {

        articleService.deleteArticleById(id);

    }

    @GetMapping("/count")
    public int getArticleCount() {
        return  articleService.getArticleCount();
    }


  @GetMapping("/{id}/picture")
  public ResponseEntity<byte[]> getArticlePicture(@PathVariable Long id) throws IOException {
      // Load the adoption object from the database
      Article article = articleService.retrieveArticleById(id);
      if (article == null) {
          return ResponseEntity.notFound().build();
      }

      // Load the image file from disk
      Path imagePath = Paths.get(article.getMedia());
      byte[] imageBytes;
      try {
          imageBytes = Files.readAllBytes(imagePath);
      } catch (IOException e) {
          return ResponseEntity.notFound().build();
      }

      // Set the content type of the response to the MIME type of the image file
      String contentType = Files.probeContentType(imagePath);
      if (contentType == null) {
          contentType = "application/octet-stream";
      }

      // Create the response entity with the image bytes and content type
      return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(imageBytes);
  }
    @PostMapping("/{id}/likes")
    public ResponseEntity<Article> incrementLikes(@PathVariable Long id) {
       return articleService.incrementLikes(id);
    }



}


