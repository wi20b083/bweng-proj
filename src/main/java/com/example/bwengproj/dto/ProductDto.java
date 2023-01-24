package com.example.bwengproj.dto;

import com.example.bwengproj.model.Product;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * A DTO for the {@link Product} entity
 */
public record ProductDto(@NotBlank String name, @NotBlank String description,
                         @NotBlank String imagePath) implements Serializable {
}