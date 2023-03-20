package com.example.pawcare.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Article implements Serializable {

    @OneToMany (mappedBy = "article", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Comment> comments;

    @ManyToOne
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idArticle;
    private String title;
    private String content;
    private Long nbViews;
    private int nblike;
    private int nbdislike;
    private int nbcomments;
    private String tags;
    private String media;
}
