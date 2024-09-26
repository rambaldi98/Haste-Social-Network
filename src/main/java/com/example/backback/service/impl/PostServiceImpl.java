package com.example.backback.service.impl;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.Post;
import com.example.backback.repository.IPostRepository;
import com.example.backback.service.IPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostServiceImpl implements IPostService {
    @Autowired
    private IPostRepository postRepository;
    @Override
    public Post save(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll();
    }

    @Override
    public List<Post> findByUser(User user) {
        return (List<Post>) postRepository.findAllByUser(user);
    }

    @Override
    public void remove(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public String getUsernameById(Long id) {
        return postRepository.findUsernameById(id);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id);
    }
}
