package com.example.backback.service.impl;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.CommentPost;
import com.example.backback.domain.entity.post.Post;
import com.example.backback.repository.ICommentPostRepository;
import com.example.backback.service.ICommentPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentPostServiceImpl implements ICommentPostService {
    @Autowired
    ICommentPostRepository commentPostRepository;
    @Override
    public CommentPost save(CommentPost commentPost) {
        return commentPostRepository.save(commentPost);
    }

    @Override
    public Optional<CommentPost> findById(long id) {
        return commentPostRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        commentPostRepository.deleteById(id);
    }

    @Override
    public Iterable<CommentPost> getAllCommentByUser(User user) {
        return commentPostRepository.findAllByUser(user);
    }

    @Override
    public Iterable<CommentPost> getAllCommentByPost(Post post) {
        return commentPostRepository.findAllByPost(post);
    }

    @Override
    @Transactional
    public void deleteAllByPost(Post post) {
        commentPostRepository.deleteAllByPost(post);
    }

    @Override
    public void delete(CommentPost commentPost) {
        commentPostRepository.delete(commentPost);
    }
}
