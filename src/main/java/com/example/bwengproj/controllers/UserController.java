package com.example.bwengproj.controllers;

import com.example.bwengproj.dto.PasswordDto;
import com.example.bwengproj.dto.UserDto;
import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.Role;
import com.example.bwengproj.security.JwtTokenUtil;
import com.example.bwengproj.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.bwengproj.util.Util.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    /**
     * Gets all {@link User} entities from the Database.
     * Only accessible for Users with {@link Role} "ROLE_ADMIN".
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the found {@link User} entities attached.
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    public ResponseEntity<?> getAll() throws JsonProcessingException {
        List<User> users = userService.getAll();
        return response(users);
    }

    /**
     * Gets a {@link User} entity from the Database.
     * Authorization required.
     * @param id The requested {@link User}'s ID.
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the found {@link User} entity attached.
     */
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        if(tokenMatchesRequest(id, token)) {
            User user = userService.get(id);
            return response(user);
        } else {
            throw new IllegalAccessException("You cannot request another user's data.");
        }
    }

    /**
     * Updates a {@link User} entity from the Database.
     * Authorization required.
     * @param id The {@link User}'s ID.
     * @param json JSON String with {@link UserDto} fields. Field "password" is ignored.
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the updated {@link User} entity attached.
     *
     */
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        if(tokenMatchesRequest(id, token)) {
            UserDto dto = objectMapper.readValue(json, UserDto.class);
            User user = userService.update(id, dto);
            return response(user);
        } else {
            throw new IllegalAccessException("You cannot update another user's data.");
        }
    }

    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        if(tokenMatchesRequest(id, token)) {
            PasswordDto dto = objectMapper.readValue(json, PasswordDto.class);
            userService.updatePassword(id, dto);
            return response(null);
        } else {
            throw new IllegalAccessException("You cannot reset another user's password.");
        }
    }

    /**
     * Deletes a {@link User} entity from the Database.
     * Authorization required.
     * @param id The {@link User}'s ID.
     * @return {@link ResponseEntity} with {@link HttpStatus} 200.
     */
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        if(tokenMatchesRequest(id, token)) {
            userService.delete(id);
            return response(null);
        } else {
            throw new IllegalAccessException("You cannot delete another user.");
        }
    }

    /**
     * @param o The {@link Object} to be attached.
     * @return A new {@link ResponseEntity} with {@link HttpStatus} 200 and the {@link Object} attached.
     * @throws JsonProcessingException The {@link Object} could not be written as a JSON String.
     */
    private ResponseEntity<?> response(Object o) throws JsonProcessingException {
        return new ResponseEntity<>(objectMapper.writeValueAsString(o), HttpStatus.OK);
    }

    private Boolean tokenMatchesRequest(Long id, String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = userService.get(username).getId();
        return userId.longValue() == id.longValue();
    }
}
