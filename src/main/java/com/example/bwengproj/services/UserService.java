package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.User;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Service
public interface UserService {

    // save operation
    User saveUser(@Valid User user);

    // read operation
    Iterable<User> fetchUserList();

    // update operation
    User updateUser(@Valid User user, Long userId);

    User updatePassword(String oldPw, String newPw, Long userId);

    // delete operation
    void deleteUserById(Long userId);

    User fetchUserById(Long userId);

    //fetch auction list by user id
    Iterable<Auction> fetchAuctionsByUser(Long userId);


    //fetch bid list by user id
    Iterable<Bid> fetchBidsByUser(Long userId);

    User fetchUserByUsername(@NotBlank @NotNull String username);

    User fetchUserByEmail(@Email String email);
}
