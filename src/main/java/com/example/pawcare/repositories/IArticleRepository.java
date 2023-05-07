package com.example.pawcare.repositories;

import com.example.pawcare.entities.Article;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
    public interface IArticleRepository extends JpaRepository<Article, Long> {

}
