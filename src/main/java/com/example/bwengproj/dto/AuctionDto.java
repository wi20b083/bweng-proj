package com.example.bwengproj.dto;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.AuctionStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Auction} entity
 */
public record AuctionDto(User user, LocalDateTime startDateTime, LocalDateTime deliveryDateTime,
                         LocalDateTime endDateTime, AuctionStatus status) implements Serializable {
}