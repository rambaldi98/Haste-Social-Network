package com.example.backback.service;



import com.example.backback.domain.entity.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String name); //Tim kiem User co ton tai trong DB khong?
    Boolean existsByUsername(String username); //username da co trong DB chua, khi tao du lieu
    Boolean existsByEmail(String email); //email da co trong DB chua
    Boolean existsByPhone(String phone);
    User save(User user);
    Optional<User> viewInfo(Long id);//xem info cua nguoi dung cu the
    boolean checkPassword(User user, String password);
    //lay ra list nguoi dung;
    Iterable<User> getAllUser();
    Iterable<User> getAllUserAndStatus();
    Optional<User> findUserById(Long id);

    Iterable<User> searchByUsername(String username);

}
