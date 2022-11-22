package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.User;

import java.util.List;

public interface UserService {

    // save operation
    User saveUser(User user);

    // read operation
    List<User> fetchUserList();

    // update operation
    User updateUser(User user, Long userId);


    // delete operation
    void deleteUserById(Long userId);


}
