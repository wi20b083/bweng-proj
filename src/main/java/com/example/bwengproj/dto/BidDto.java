package com.example.bwengproj.dto;

import com.example.bwengproj.model.Bid;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Bid} entity
 */
public record BidDto(Long userId, LocalDateTime deliveryDateTime,
                     Long auctionId) implements Serializable {
}