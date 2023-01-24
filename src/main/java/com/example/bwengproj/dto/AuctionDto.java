package com.example.bwengproj.dto;

import com.example.bwengproj.model.Auction;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Auction} entity
 */
public record AuctionDto(Long userId, LocalDateTime startDateTime, LocalDateTime deliveryDateTime,
                         LocalDateTime endDateTime) implements Serializable {
}