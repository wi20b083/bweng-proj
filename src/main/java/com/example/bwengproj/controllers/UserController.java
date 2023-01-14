package com.example.bwengproj.controllers;

import com.example.bwengproj.model.User;
import com.example.bwengproj.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

import static com.example.bwengproj.BwengProjApplication.objectMapper;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public ResponseEntity<?> register(String json) {
        return null;
    }


    @GetMapping("/users/all")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_ADMIN)")
    public ResponseEntity<?> getAll() throws JsonProcessingException {
        return new ResponseEntity<>(
                objectMapper.writer().writeValueAsString(userService.fetchUserList()), HttpStatus.ACCEPTED);
    }

    @PutMapping(value = "/users/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.Role).ROLE_USER)")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestParam(name = "pwchange", required = false) Optional<Boolean> pwchange, @RequestParam(name = "state", required = false) Optional<Boolean> state, String json) throws JsonProcessingException {
        User userDB = userService.fetchUserById(id);
        if (pwchange.isPresent()) {
            if (pwchange.get()) {
                ObjectNode node = objectMapper.readValue(json, ObjectNode.class);
                if (node.has("password")) {
                    updateUserPassword(id, node.get("password").asText());
                }
            }
        }
        return null;
    }

    private void updateUserPassword(Long id, String password) {
    }

    private void changeUserStatus(Long id, int state) {
    }

    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.Role).ROLE_USER)")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        return null;
    }


}
