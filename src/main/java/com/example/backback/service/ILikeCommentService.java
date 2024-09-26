package com.example.backback.service;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.CommentPost;
import com.example.backback.domain.entity.post.LikeComment;

import java.util.Optional;

public interface ILikeCommentService {
    Optional<LikeComment> findByUserAndComment(User user, CommentPost commentPost);
    void save(LikeComment likeComment);
    void delete(LikeComment likeComment);
//    void deleteAllByCommentPost(Long comment_id);
    void deleteAllByCommentPost(CommentPost comment_id);
    Iterable<LikeComment> findAllByCommentPost(CommentPost commentPost);
}
