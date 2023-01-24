package com.example.bwengproj.services;

import com.example.bwengproj.dto.BidDto;
import com.example.bwengproj.dto.BidItemDto;
import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.status.BidStatus;

import java.util.List;

public interface BidService {
    Bid save(Bid bid);
    Bid create(BidDto dto);
    List<Bid> getAll(Long auctionId);
    Bid get(Long id);
    Bid update(Long id, BidDto dto);
    void delete(Long id);
    void delete(Auction auction);
    void deleteAll();
    Bid add(Long bidId, BidItemDto dto);
    void changeStatus(Long id, BidStatus status);
}
