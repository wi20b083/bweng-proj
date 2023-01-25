package com.example.bwengproj.dto;

import com.example.bwengproj.model.User;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link User} entity
 */
public record UserDto(@NotBlank String firstname, @NotBlank String lastname, @NotBlank @Email String email,
                      @NotBlank String username, String password,
                      @NotBlank String imagePath) implements Serializable {
}