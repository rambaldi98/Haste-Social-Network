package com.example.backback.service.impl;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.CommentPost;
import com.example.backback.domain.entity.post.LikeComment;
import com.example.backback.repository.ILikeCommentRepository;
import com.example.backback.service.ILikeCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeCommentServiceImpl implements ILikeCommentService {
    @Autowired
    ILikeCommentRepository likeCommentRepository;
    @Override
    public Optional<LikeComment> findByUserAndComment(User user, CommentPost commentPost) {
        return likeCommentRepository.findByUserAndCommentPost(user,commentPost);
    }

    @Override
    public void save(LikeComment likeComment) {
        likeCommentRepository.save(likeComment);
    }

    @Override
    public void delete(LikeComment likeComment) {
        likeCommentRepository.delete(likeComment);
    }

//    @Override
//    public void deleteAllByCommentPost(Long comment_id) {
//        likeCommentRepository.deleteAllByCommentPost(comment_id);
//    }
@Override
@Transactional
public void deleteAllByCommentPost(CommentPost comment_id) {
    likeCommentRepository.deleteAllByCommentPost(comment_id);
}

    @Override
    public Iterable<LikeComment> findAllByCommentPost(CommentPost commentPost) {
        return likeCommentRepository.findAllByCommentPost(commentPost);
    }
}
