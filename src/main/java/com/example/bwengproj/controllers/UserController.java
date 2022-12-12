package com.example.bwengproj.controllers;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.User;
import com.example.bwengproj.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    // /users
    // Save operation
    @PostMapping("/users")
    public User saveUser(@Valid @RequestBody User user) {

        return userService.saveUser(user);
    }

    // Read operation
    @GetMapping("/users")
    public Iterable<User> fetchUserList() {
        return userService.fetchUserList();
    }


    // /users/id
    // Update operation
    @PutMapping("/users/{id}")
    public User updateUser(@Valid @RequestBody User user, @PathVariable("id") Long userId) {
        return userService.updateUser(user, userId);
    }

    // Delete operation
    @DeleteMapping("/users/{id}")
    public String deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return "Deleted Successfully";
    }

    // get by id
    @GetMapping("/users/{id}")
    public User fetchUserByID(@PathVariable("id") Long userId) {
        return userService.fetchUserById(userId);
    }

    // /users/id/data
    @GetMapping("/users/{id}/auctions")
    public Iterable<Auction> fetchAuctionsByUser(@PathVariable("id") Long userId) {
        return userService.fetchAuctionsByUser(userId);
    }

    @GetMapping("/users/{id}/bids")
    public Iterable<Bid> fetchBidsByUser(@PathVariable("id") Long userId) {
        return userService.fetchBidsByUser(userId);
    }
}
