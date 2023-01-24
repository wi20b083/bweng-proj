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
    @PreAuthorize("hasAnyRole(T(com.example.bwengproj.model.status.Role).ROLE_USER, T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @GetMapping("/users/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(id, token)) throw new IllegalAccessException("You cannot request another user's data.");

        User user = userService.get(id);
        return response(user);
    }

    /**
     * Updates a {@link User} entity from the Database.
     * Authorization required.
     * @param id The {@link User}'s ID.
     * @param json JSON String with {@link UserDto} fields. Field "password" is ignored.
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the updated {@link User} entity attached.
     *
     */
    @PreAuthorize("hasAnyRole(T(com.example.bwengproj.model.status.Role).ROLE_USER, T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(id, token)) throw new IllegalAccessException("You cannot request another user's data.");

        UserDto dto = objectMapper.readValue(json, UserDto.class);
        User user = userService.update(id, dto);
        return response(user);
    }

    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        if(!tokenMatchesRequest(id, token)) throw new IllegalAccessException("You cannot reset another user's password.");

        PasswordDto dto = objectMapper.readValue(json, PasswordDto.class);
        userService.updatePassword(id, dto);
        return response(null);
    }

    /**
     * Deletes a {@link User} entity from the Database.
     * Authorization required.
     * @param id The {@link User}'s ID.
     * @return {@link ResponseEntity} with {@link HttpStatus} 200.
     */
    @PreAuthorize("hasAnyRole(T(com.example.bwengproj.model.status.Role).ROLE_USER, T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(id, token)) throw new IllegalAccessException("You cannot request another user's data.");

        userService.delete(id);
        return response(null);
    }

    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @PutMapping("/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestParam Boolean status) throws JsonProcessingException {
        userService.changeStatus(id, status);
        return response(null);
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
