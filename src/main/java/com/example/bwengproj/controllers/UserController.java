package com.example.bwengproj.controllers;

import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.User;
import com.example.bwengproj.services.BidService;
import com.example.bwengproj.services.UserService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
    //@Autowired
    private UserService userService;

    // Save operation
    @PostMapping("/users")
    public User saveUser(@Valid @RequestBody User user){

        return userService.saveUser(user);
    }

    // Read operation
    @GetMapping("/users")
    public Iterable<User> fetchUserList(){
        return userService.fetchUserList();
    }

    // Update operation
    @PutMapping("/users/{id}")
    public User updateUser(@RequestBody User user, @PathVariable("id") long userId){
        return userService.updateUser(user, userId);
    }

    // Delete operation
    @DeleteMapping("/users/{id}")
    public String deleteUserById(@PathVariable("id") long userId){
        userService.deleteUserById(userId);
        return "Deleted Successfully";
    }

    // get by id
    @GetMapping("/users/{id}")
    public User fetchUserByID(@PathVariable("id") long userId){
        return userService.fetchUserById(userId);
    }
}
