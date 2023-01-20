package com.example.bwengproj.controllers;

import com.example.bwengproj.model.User;
import com.example.bwengproj.security.JwtTokenUtil;
import com.example.bwengproj.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

import static com.example.bwengproj.util.Util.toPojo;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PutMapping(value = "/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_USER)")
    public ResponseEntity<?> updateUser(@PathVariable Long id, String json, @RequestHeader(name = "Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        System.out.println(token);
        if (idMatchesUser(id, token)) {
            User u = toPojo(json, User.class);
            return ResponseEntity.ok(userService.updateUser(id, u));
        } else {
            throw new IllegalAccessException("You cannot modify data of another user.");
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_USER)")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, @RequestHeader(name = "Authorization") String token) throws IllegalAccessException {
        if (idMatchesUser(id, token)) {
            userService.deleteUserById(id);
            return new ResponseEntity<>("User deleted.", HttpStatus.OK);
        } else {
            throw new IllegalAccessException("You cannot delete another user.");
        }
    }

    private boolean idMatchesUser(Long id, String token) {
        token = token.substring(7);
        return Objects.equals(userService.fetchUserByUsername(jwtTokenUtil.getUsernameFromToken(token)).getId(), id);
    }
}
