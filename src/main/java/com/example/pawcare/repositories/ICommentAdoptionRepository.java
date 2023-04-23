package com.example.pawcare.repositories;

import com.example.pawcare.entities.CommentAdoption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentAdoptionRepository extends JpaRepository<CommentAdoption,Long> {
    List<CommentAdoption> findByAdoptionIdAdoption(long idAdoption);

}
