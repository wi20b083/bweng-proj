package com.example.bwengproj.controllers;

import com.example.bwengproj.dto.AuthenticationDto;
import com.example.bwengproj.dto.UserDto;
import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.UserStatus;
import com.example.bwengproj.security.JwtTokenUtil;
import com.example.bwengproj.services.UserDetailsServiceImplementation;
import com.example.bwengproj.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import static com.example.bwengproj.util.Util.objectMapper;
import static com.example.bwengproj.util.Util.response;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsServiceImplementation userDetailsService;

    @Autowired
    private UserService userService;

    /**
     * Reads a {@link User}'s credentials and creates a JWT token,
     * Authentication: none,
     * Authorization: all
     *
     * @param json JSON String with {@link AuthenticationDto} fields.
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the created JWT attached.
     */
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody String json, HttpServletRequest request) throws Exception {
        AuthenticationDto dto = objectMapper.readValue(json, AuthenticationDto.class);
        authenticate(dto.username(), dto.password());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(dto.username());

        final String token = objectMapper.writeValueAsString(jwtTokenUtil.generateToken(userDetails, request));

        return response(token);
    }

    /**
     * Saves a new {@link User} entity in the Database,
     * Authentication: none,
     * Authorization: all
     *
     * @param json JSON String with {@link UserDto} fields. Field "password" must not be blank
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the created {@link User} entity attached
     * @throws JsonProcessingException JSON String does not match {@link UserDto} fields
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody String json) throws JsonProcessingException {
        UserDto dto = objectMapper.readValue(json, UserDto.class);
        User user = userService.create(dto);
        return response(user);
    }

    /**
     * Authenticates a {@link User}
     *
     * @param username The {@link User}'s username
     * @param password The {@link User}'s password
     * @throws Exception The {@link User} has the {@link UserStatus} "USER_LOCKED" or the {@link User}'s credentials are wrong
     */
    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_LOCKED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
