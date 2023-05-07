package com.example.pawcare.repositories;

import com.example.pawcare.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
        List<Comment> findByArticleId(Long id);
    }


