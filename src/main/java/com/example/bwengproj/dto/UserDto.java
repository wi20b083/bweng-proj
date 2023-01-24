package com.example.bwengproj.dto;

import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.Role;
import com.example.bwengproj.model.status.UserStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link User} entity for updating a {@link User}
 */
public record UserDto(@NotBlank String firstname, @NotBlank String lastname, @NotBlank @Email String email,
                      @NotBlank String username, String password,
                      @NotBlank String imagePath) implements Serializable {
}