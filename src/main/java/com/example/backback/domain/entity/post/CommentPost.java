package com.example.backback.domain.entity.post;

import com.example.backback.domain.entity.User;
import lombok.Data;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.Instant;

@Data
@Entity
@Table(name = "comment_post")
public class CommentPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @NotEmpty
    private String text;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "postId")
    private Post post;

    private Instant createdDate;
    private Instant timeUpdate;
    private Integer like_comment_count = 0;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId")
    private User user;

    public CommentPost(String text, Post post, Instant createdDate) {
        this.text = text;
        this.post = post;
        this.createdDate = createdDate;
    }

    public CommentPost(String text, Post post, Instant createdDate, Instant timeUpdate, User user) {
        this.text = text;
        this.post = post;
        this.createdDate = createdDate;
        this.timeUpdate = timeUpdate;
        this.user = user;
    }

    public CommentPost() {

    }

    public CommentPost(String text, Instant createdDate) {
        this.text = text;
        this.createdDate = createdDate;
//        this.timeUpdate = Instant.now();
    }

    public CommentPost(String text, Instant createdDate, String update) {
        this.text = text;
        this.timeUpdate = createdDate;
    }
}
