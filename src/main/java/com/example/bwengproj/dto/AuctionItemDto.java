package com.example.bwengproj.dto;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.AuctionItem;
import com.example.bwengproj.model.Product;

import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * A DTO for the {@link AuctionItem} entity
 */
public record AuctionItemDto(Auction auction, Product product, @Positive Integer amount, @Positive Integer costPerUnit,
                             @Positive Integer total) implements Serializable {
}