package com.example.backback.repository;

import com.example.backback.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.springframework.data.repository.query.parser.Part.Type.LIKE;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String name); //Tim kiem User co ton tai trong DB khong?
    Boolean existsByUsername(String username); //username da co trong DB chua, khi tao du lieu
    Boolean existsByEmail(String email); //email da co trong DB chua
    Boolean existsByPhone(String phone);

    //xem info nguoi khac theo id
@Query(value = "Select u from User u  left join Friend b on u.id= b.usertwo.id  where b.status is null")
    Iterable<Object> getAllUserAndStatus();


    @Query("Select u from User u where u.username LIKE  %?1%")
            Iterable<User> searchByUsername(String username);

// quyery trung gian
//    select users.id from users join friend
//    where users.id != 1 and( (users.id = friend.userone_id) or (users.id = friend.usertwo_id))
//    @Query()
//    @Query()
}