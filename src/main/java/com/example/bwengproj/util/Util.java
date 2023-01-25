package com.example.bwengproj.util;

import com.example.bwengproj.security.JwtTokenUtil;
import com.example.bwengproj.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


public class Util {
    public static final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .registerModule(new ParameterNamesModule())
            .registerModule(new Jdk8Module());

    /**
     * @param o The {@link Object} to be attached.
     * @return A new {@link ResponseEntity} with {@link HttpStatus} 200 and the {@link Object} attached.
     * @throws JsonProcessingException The {@link Object} could not be written as a JSON String.
     */
    public static ResponseEntity<?> response(Object o) throws JsonProcessingException {
        return new ResponseEntity<>(objectMapper.writeValueAsString(o), HttpStatus.OK);
    }

    /**
     * Matches the JWT's subject's ID with a given ID
     *
     * @param id    ID to compare
     * @param token JWT
     * @return whether the IDs match or don't
     */
    public static Boolean tokenMatchesRequest(Long id, String token, JwtTokenUtil jwtTokenUtil, UserService userService) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = userService.get(username).getId();
        return userId.longValue() == id.longValue();
    }
}
