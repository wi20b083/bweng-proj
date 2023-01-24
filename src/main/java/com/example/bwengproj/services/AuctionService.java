package com.example.bwengproj.services;

import com.example.bwengproj.dto.AuctionDto;
import com.example.bwengproj.dto.AuctionItemDto;
import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.AuctionStatus;

import java.util.List;

public interface AuctionService {
    Auction save(Auction auction);
    Auction create(AuctionDto dto);
    List<Auction> getAll();
    List<Auction> getAll(User user);
    Auction get(Long id);
    Auction update(Long id, AuctionDto dto);
    void delete(Long id);
    void deleteAll();
    Auction add(Long auctionId, AuctionItemDto dto);

    void changeStatus(Long id, AuctionStatus status);
}
