package com.example.bwengproj.dto;

import com.example.bwengproj.model.AuctionItem;

import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * A DTO for the {@link AuctionItem} entity
 */
public record AuctionItemDto(Long productId, @Positive Integer amount,
                             @Positive Integer costPerUnit) implements Serializable {
}