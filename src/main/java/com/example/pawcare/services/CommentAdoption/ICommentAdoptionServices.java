package com.example.pawcare.services.CommentAdoption;

import com.example.pawcare.entities.CommentAdoption;

import java.util.List;

public interface ICommentAdoptionServices {

    public CommentAdoption addCommentToAdoption(Long idAdoption, CommentAdoption commentAdoption);
    public List<CommentAdoption> getCommentsForAdoption(Long idAdoption);

}
