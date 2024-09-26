package com.example.backback.repository;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IPostRepository extends JpaRepository<Post,Long> {
    Iterable<Post> findAllByUser(User user);

    @Query(value = "SELECT u.username FROM post" +
            " join users  u on post.user_id = u.id " +
            "WHERE post.id = :id",nativeQuery = true)
    String findUsernameById(@Param("id") Long id);

}
