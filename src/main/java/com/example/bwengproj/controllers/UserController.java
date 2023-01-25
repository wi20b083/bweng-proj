package com.example.bwengproj.controllers;

import com.example.bwengproj.dto.PasswordDto;
import com.example.bwengproj.dto.UserDto;
import com.example.bwengproj.model.Bid;
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
     * Gets all {@link User} entities from the Database,
     * Authentication: authenticated,
     * Authorization: {@link Role} "ROLE_ADMIN"
     *
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the found {@link User} entities attached
     */
    @GetMapping("/users")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    public ResponseEntity<?> getAll() throws JsonProcessingException {
        List<User> users = userService.getAll();
        return response(users);
    }

    /**
     * Gets a {@link User} entity from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @param id    The requested {@link User}'s ID.
     * @param token JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the found {@link User} entity attached.
     * @throws IllegalAccessException The requesting {@link User} tried to get data that belongs to another {@link User}
     */
    @GetMapping("/users/{id}")
    public ResponseEntity<?> get(@PathVariable Long id, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(id, token, jwtTokenUtil, userService))
            throw new IllegalAccessException("You cannot request another user's data.");

        User user = userService.get(id);
        return response(user);
    }

    /**
     * Updates a {@link User} entity from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @param id    The {@link User}'s ID
     * @param json  JSON String with {@link UserDto} fields. Field "password" is ignored
     * @param token JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the updated {@link User} entity attached
     * @throws IllegalAccessException The requesting {@link User} tried to update data from to another {@link User}
     */
    @PutMapping("/users/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(id, token, jwtTokenUtil, userService))
            throw new IllegalAccessException("You cannot update another user's data.");

        UserDto dto = objectMapper.readValue(json, UserDto.class);
        User user = userService.update(id, dto);
        return response(user);
    }

    /**
     * Updates a {@link User} entity from the Database,
     * Authentication: authenticated,
     * Authorization: {@link Role} "ROLE_USER"
     *
     * @param id    The {@link User}'s ID
     * @param json  JSON String with {@link PasswordDto} fields
     * @param token JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200
     * @throws IllegalAccessException The requesting {@link User} tried to reset the password of another {@link User}
     */
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    @PutMapping("/reset-password/{id}")
    public ResponseEntity<?> updatePassword(@PathVariable Long id, @RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        if (!tokenMatchesRequest(id, token, jwtTokenUtil, userService))
            throw new IllegalAccessException("You cannot reset another user's password.");

        PasswordDto dto = objectMapper.readValue(json, PasswordDto.class);
        userService.updatePassword(id, dto);
        return response(null);
    }

    /**
     * Deletes a {@link User} entity from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @param id The {@link User}'s ID
     * @return {@link ResponseEntity} with {@link HttpStatus} 200
     * @throws IllegalAccessException The requesting {@link User} tried to delete another {@link User}
     */
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(id, token, jwtTokenUtil, userService))
            throw new IllegalAccessException("You cannot delete another user.");

        userService.delete(id);
        return response(null);
    }

    /**
     * Changes the status of an {@link Bid} entity from the Database,
     * Authentication: authenticated,
     * Authorization: {@link Role} "ROLE_ADMIN"
     *
     * @param id     ID of the {@link User} to change the status of
     * @param status Status to set
     * @return {@link ResponseEntity} with {@link HttpStatus} 200
     */
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    @PutMapping("/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestParam Boolean status) throws JsonProcessingException {
        userService.changeStatus(id, status);
        return response(null);
    }
}
