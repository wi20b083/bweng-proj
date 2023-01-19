package com.example.bwengproj.controllers;

import com.example.bwengproj.model.User;
import com.example.bwengproj.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.bwengproj.util.Util.toJson;
import static com.example.bwengproj.util.Util.toPojo;

@RestController
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/users/all")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_ADMIN)")
    public ResponseEntity<?> getAll() throws JsonProcessingException {
        return new ResponseEntity<>(
                toJson(userService.fetchUserList()), HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.Role).ROLE_USER)")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestParam(name = "pwchange", required = false) Optional<Boolean> pwchange, @RequestParam(name = "state", required = false) Optional<Boolean> state, String json) throws JsonProcessingException {
        User u = toPojo(json, User.class);
        return new ResponseEntity<>(toJson(userService.updateUser(u, id)), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.Role).ROLE_USER)")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }


}
