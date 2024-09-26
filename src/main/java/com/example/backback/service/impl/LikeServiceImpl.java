package com.example.backback.service.impl;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.LikePost;
import com.example.backback.domain.entity.post.Post;
import com.example.backback.repository.ILikeRepository;
import com.example.backback.service.ILikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeServiceImpl implements ILikeService {
    @Autowired
    private ILikeRepository likeRepository;

    @Override
    public Optional<LikePost> findByUser(User user) {

        return likeRepository.findByUser(user);
    }

    @Override
    @Transactional
    public Optional<LikePost> findByUserAndPost(User user, Post post) {
        return likeRepository.findByUserAndPost(user,post);
    }

    @Override
    public void save(LikePost likePost) {
        likeRepository.save(likePost);
    }

    @Override
    public void delete(LikePost likePost) {
        likeRepository.delete(likePost);
    }

    @Override
    @Transactional
    public void deleteAllByPost(Post post) {
        likeRepository.deleteAllByPost(post);
    }
}
