package com.example.backback.mapper;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.CommentPost;
import com.example.backback.domain.entity.post.Post;
import com.example.backback.dto.request.CommentPostCreate;

import java.time.Instant;

public class CommentMapper {
    private Long id;
    private String text;
    private Post post;
    private Instant createdDate;
    private Instant timeUpdate;

    public CommentMapper(String text, Post post, Instant createdDate, Instant timeUpdate) {
        this.text = text;
        this.post = post;
        this.createdDate = createdDate;
        this.timeUpdate = timeUpdate;
    }

    public CommentMapper(String text, Post post, Instant createdDate) {
        this.text = text;
        this.post = post;
        this.createdDate = createdDate;
    }

    public static CommentPost build(CommentPostCreate postCreate ){
        return new CommentPost(
                postCreate.getText(),
                Instant.now()
        );
    }

    public static CommentPost buildUpdate(CommentPostCreate postCreate){
        return new CommentPost(
                postCreate.getText(),
                Instant.now(),
                "a"
        );
    }
}
