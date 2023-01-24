package com.example.bwengproj.dto;

import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.Role;
import com.example.bwengproj.model.status.UserStatus;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

/**
 * A DTO for the {@link User} entity
 */
public record UserDto(@NotBlank String firstname, @NotBlank String lastname, @NotBlank @Email String email,
                      @NotBlank String username, @NotBlank String password, Set<Role> roles, UserStatus status,
                      @NotBlank String imagePath) implements Serializable {
}