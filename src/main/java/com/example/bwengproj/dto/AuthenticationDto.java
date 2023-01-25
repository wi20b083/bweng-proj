package com.example.bwengproj.dto;

import javax.validation.constraints.NotBlank;

/**
 * A DTO for the authentication process
 */
public record AuthenticationDto(@NotBlank String username, @NotBlank String password) {
}
