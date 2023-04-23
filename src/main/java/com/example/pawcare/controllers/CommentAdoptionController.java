package com.example.pawcare.controllers;

import com.example.pawcare.entities.CommentAdoption;
import com.example.pawcare.services.CommentAdoption.BadWordsFilter;
import com.example.pawcare.services.CommentAdoption.CommentAdoptionServiceImpl;
import com.example.pawcare.services.CommentAdoption.ICommentAdoptionServices;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@CrossOrigin("*")
@RequestMapping("/commentAdoption")

public class CommentAdoptionController {

    @Autowired
    private ICommentAdoptionServices commentAdoptionServices;

    @PostMapping("/{idAdoption}")

    public ResponseEntity<CommentAdoption> addCommentToAdoption(
            @PathVariable(value = "idAdoption") Long idAdoption,
            @RequestBody @Valid CommentAdoption comment) {
        try {
            CommentAdoption savedComment = commentAdoptionServices.addCommentToAdoption(idAdoption, comment);
            return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    @GetMapping("/{idAdoption}")
    public ResponseEntity<List<CommentAdoption>> getCommentsForAdoption(@PathVariable(value = "idAdoption") Long idAdoption) {
        try {
            List<CommentAdoption> comments = commentAdoptionServices.getCommentsForAdoption(idAdoption);
            return new ResponseEntity<>(comments, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}







