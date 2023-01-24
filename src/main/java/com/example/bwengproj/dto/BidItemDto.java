package com.example.bwengproj.dto;

import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.BidItem;
import com.example.bwengproj.model.Product;

import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * A DTO for the {@link BidItem} entity
 */
public record BidItemDto(Bid bid, Product product, @Positive Integer amount, @Positive Integer costPerUnit,
                         @Positive Integer total) implements Serializable {
}