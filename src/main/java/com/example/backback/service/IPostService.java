package com.example.backback.service;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.Post;

import java.util.List;
import java.util.Optional;

public interface IPostService {
    Post save(Post post);
    List<Post> findAll();
    List<Post> findByUser(User user);
    void remove(Long id);
    String getUsernameById(Long id);
    Optional<Post> findById(Long id);
}
