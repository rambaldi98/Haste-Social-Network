package com.example.backback.repository;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.CommentPost;
import com.example.backback.domain.entity.post.LikeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.websocket.server.PathParam;
import java.util.Optional;

@Repository
public interface ILikeCommentRepository extends JpaRepository<LikeComment,Long> {

    Optional<LikeComment> findByUserAndCommentPost(User user, CommentPost commentPost);

    Iterable<LikeComment> findAllByCommentPost(CommentPost commentPost);
//    @Modifying
//    @Query(name = "delete from like_comment where comment_id = :id",nativeQuery = true)
//    void deleteAllByCommentPost(@PathParam("id") Long id);
    void deleteAllByCommentPost(CommentPost commentPost);
}
