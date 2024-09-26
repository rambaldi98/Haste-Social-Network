package com.example.backback.service;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.CommentPost;
import com.example.backback.domain.entity.post.Post;

import java.util.Optional;

public interface ICommentPostService {
    CommentPost save(CommentPost commentPost);
    Optional<CommentPost> findById(long id);
    void deleteById(Long id);
    Iterable<CommentPost> getAllCommentByUser(User user);
    Iterable<CommentPost> getAllCommentByPost(Post post);

    void deleteAllByPost(Post post);
    void delete(CommentPost commentPost);
}
