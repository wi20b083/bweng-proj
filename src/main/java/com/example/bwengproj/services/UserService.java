package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.User;

public interface UserService {

    // save operation
    User saveUser(User user);

    // read operation
    Iterable<User> fetchUserList();

    // update operation
    User updateUser(User user, Long userId);


    // delete operation
    void deleteUserById(Long userId);

    User fetchUserById(Long userId);

    //fetch auction list by user id
    Iterable<Auction> fetchAuctionsByUser(Long userId);


    //fetch bid list by user id
    Iterable<Bid> fetchBidsByUser(Long userId);


}
