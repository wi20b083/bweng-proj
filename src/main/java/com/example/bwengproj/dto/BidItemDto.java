package com.example.bwengproj.dto;

import com.example.bwengproj.model.BidItem;

import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * A DTO for the {@link BidItem} entity
 */
public record BidItemDto(Long productId, @Positive Integer amount, @Positive Integer costPerUnit,
                         @Positive Integer total) implements Serializable {
}