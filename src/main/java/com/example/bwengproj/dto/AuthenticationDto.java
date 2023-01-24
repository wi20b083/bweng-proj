package com.example.bwengproj.dto;

import javax.validation.constraints.NotBlank;

public record AuthenticationDto(@NotBlank String username, @NotBlank String password) {
}
