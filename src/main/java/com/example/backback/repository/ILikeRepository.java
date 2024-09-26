package com.example.backback.repository;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.LikePost;
import com.example.backback.domain.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ILikeRepository extends JpaRepository<LikePost, Long> {

    Optional<LikePost> findByUser(User user);
    Optional<LikePost> findByUserAndPost(User user, Post post);
    void deleteAllByPost(Post post);
}
