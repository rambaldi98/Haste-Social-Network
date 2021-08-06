package com.example.backback.repository;

import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.friend.Friend;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IFriendshipRepository extends JpaRepository<Friend, Long> {

    Optional<Friend> findByUseroneAndAndUsertwo(User userOne, User userTwo);
    @Query(value = "select * from Friend " +
            "where ((userone_id = :user) or (usertwo_id = :user))and (status = :status) ", nativeQuery = true)
    Iterable<Friend> findAllFriendByStatus(@Param("user") Long user_id, @Param("status") Integer status);
}