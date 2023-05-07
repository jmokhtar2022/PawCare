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
    private Long id;
    private String title;
    private String content;
    private int nbviews=0;
    private int nblike=0;
    private int nbdislike=0;
    private int nbcomments=0;
    private String tags;
    private String media;

    public void incrementNbComments() {
        this.nbcomments++;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }
}
