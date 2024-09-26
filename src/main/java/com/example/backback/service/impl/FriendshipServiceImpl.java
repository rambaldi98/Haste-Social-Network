package com.example.backback.service.impl;


import com.example.backback.domain.entity.User;
import com.example.backback.domain.entity.friend.Friend;
import com.example.backback.repository.IFriendshipRepository;
import com.example.backback.service.IFriendShipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class FriendshipServiceImpl implements IFriendShipService {
    @Autowired
    IFriendshipRepository friendshipRepository;
    @Override
    public Iterable<Friend> findAll() {
        return friendshipRepository.findAll();
    }

    @Override
    public Friend save(Friend friend) {
        return friendshipRepository.save(friend);
    }

    @Override
    public Optional<Friend> findById(Long id) {
        return friendshipRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        friendshipRepository.deleteById(id);
    }

    @Override
    public Iterable<Friend> findAllByUser() {
        return null;
    }

    @Override
    public Optional<Friend> findByUseroneAndAndUsertwo(User userOne, User userTwo) {
        return friendshipRepository.findByUseroneAndAndUsertwo(userOne,userTwo);
    }

    @Override
    @Transactional
    public List<Friend> findAllFriendByStatus(Long user_id, Integer status) {
        return friendshipRepository.findAllFriendByStatus(user_id,status);
    }

    @Override
    public Iterable<Friend> findAllFriendByUserOneStatus(Long user_id, Integer status) {
        return friendshipRepository.findAllFriendByByUserOneStatus(user_id, status);
    }

    @Override
    public Iterable<Friend> findAllFriendByUserTwoStatus(Long user_id, Integer status) {
        return friendshipRepository.findAllFriendByByUserTwoStatus(user_id,status);
    }


}
