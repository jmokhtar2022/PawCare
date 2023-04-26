package com.example.pawcare.services.CommentAdoption;

import com.example.pawcare.entities.Adoption;
import com.example.pawcare.entities.CommentAdoption;
import com.example.pawcare.repositories.IAdoptionRepository;
import com.example.pawcare.repositories.ICommentAdoptionRepository;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class CommentAdoptionServiceImpl implements ICommentAdoptionServices {

    @Autowired
    private ICommentAdoptionRepository commentAdoptionRepository;
    @Autowired
    private IAdoptionRepository adoptionRepository;

    private CommentFilter commentFilter;


    private CommentAdoptionServiceImpl(){}

    @Override

    public CommentAdoption addCommentToAdoption(Long idAdoption, CommentAdoption comment) {
        Adoption adoption = adoptionRepository.findById(idAdoption)
                .orElseThrow(() -> new RuntimeException("Adoption not found with id " + idAdoption));
        String commentText = comment.getText().trim();
        if (commentText.isEmpty()) {
            throw new IllegalArgumentException("Comment text cannot be empty");
        }
        comment.setCDate(new Date());
        comment.setAdoption(adoption);
        return commentAdoptionRepository.save(comment);
    }



    @Override
    public List<CommentAdoption> getCommentsForAdoption(Long idAdoption) {
        List<CommentAdoption> comments = commentAdoptionRepository.findByAdoptionIdAdoption(idAdoption);
        List<CommentAdoption> filteredComments = new ArrayList<>();
        for (CommentAdoption comment : comments) {
            if (!comment.getText().contains("***")) {
                filteredComments.add(comment);
            }
        }

        return filteredComments;
    }


}
