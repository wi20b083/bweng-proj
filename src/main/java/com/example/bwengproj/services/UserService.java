package com.example.bwengproj.services;

import com.example.bwengproj.model.User;


public interface UserService {

    // save operation
    User saveUser(User user);

    // update operation
    User updateUser(Long userId, User user);

    void updateUserPassword(Long userId, String Password);

    // delete operation
    void deleteUserById(Long userId);

    User fetchUserByUsername(String username);
}
