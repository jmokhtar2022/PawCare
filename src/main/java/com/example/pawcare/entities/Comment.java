package com.example.pawcare.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.*;
import java.util.*;

@Entity
@Getter
@Setter
public class Comment implements Serializable {
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "article_id_article ")
    private Article article;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment;
    private  String content;
    private String blockedwords;
    private int nblike;
    @Temporal(TemporalType.DATE)
    private Date created_at;
    @Temporal(TemporalType.DATE)
    private Date updated_at;

    public Comment(String content) {
        this.content = BlockedWords.replaceBlockedWords(content);
    }

    public Comment() {

    }


    public void setContent(String content) {
        this.content = BlockedWords.replaceBlockedWords(content);
    }

    public String getContent() {
        return BlockedWords.replaceBlockedWords(content);
    }
}
