package com.example.bwengproj.services;

import com.example.bwengproj.dto.BidDto;
import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;

import java.util.List;

public interface BidService {
    Bid save(Bid bid);
    List<Bid> getAll(Auction auction);
    Bid get(Long id);
    Bid update(Long id, BidDto dto);
    void delete(Long id);
    void delete(Auction auction);
    void deleteAll();
}
