package com.example.backback.service;


import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.LikeComment;
import com.example.backback.domain.entity.post.LikePost;
import com.example.backback.domain.entity.post.Post;

import java.util.Optional;

public interface ILikeService {
    Optional<LikePost> findByUser(User user);
    Optional<LikePost> findByUserAndPost(User user, Post post);
    void save(LikePost likePost);
    void delete(LikePost likePost);
    void deleteAllByPost(Post post);
}
