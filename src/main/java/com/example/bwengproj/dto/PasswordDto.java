package com.example.bwengproj.dto;

import com.example.bwengproj.model.User;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link User} entity for updating their password
 */

public record PasswordDto(@NotBlank String oldPassword, @NotBlank String newPassword) implements Serializable {
}
