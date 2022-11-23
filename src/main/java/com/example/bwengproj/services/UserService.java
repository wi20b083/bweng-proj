package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.User;

import java.util.Set;

public interface UserService {

    // save operation
    User saveUser(User user);

    // read operation
    Set<User> fetchUserList();

    // update operation
    User updateUser(User user, Long userId);


    // delete operation
    void deleteUserById(Long userId);

    //fetch auction list by user id
    Set<Auction> fetchAuctionsByUser(Long userId);


    //fetch bid list by user id
    Set<Bid> fetchBidsByUser(Long userId);


}
