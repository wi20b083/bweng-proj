package com.example.bwengproj.dto;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.BidStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * A DTO for the {@link Bid} entity
 */
public record BidDto(Long userId, LocalDateTime deliveryDateTime,
                     Long auctionId) implements Serializable {
}