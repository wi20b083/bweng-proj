package com.example.bwengproj.controllers;

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
    @PutMapping("/users/id/{id}")
    public User updateUser(@Valid @RequestBody User user, @PathVariable("id") Long userId) {
        return userService.updateUser(user, userId);
    }

    // Delete operation
    @DeleteMapping("/users/id/{id}")
    public String deleteUserById(@PathVariable("id") Long userId) {
        userService.deleteUserById(userId);
        return "Deleted Successfully";
    }

    // get by id
    @GetMapping("/users/id/{id}")
    public User fetchUserByID(@PathVariable("id") Long userId) {
        return userService.fetchUserById(userId);
    }

    @GetMapping("/users/username/{username}")
    public User fetchUserByUserName(@PathVariable("username") String username) {
        return userService.fetchUserByUsername(username);
    }

    //@PostMapping("/login")
    //public String login(@RequestBody )
}
