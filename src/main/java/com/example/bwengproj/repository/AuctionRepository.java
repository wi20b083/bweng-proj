package com.example.bwengproj.repository;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.validation.Valid;

@Repository
public interface AuctionRepository extends CrudRepository<Auction, Long> {
    Auction addBidToAuctionById(@Valid Bid bid, Long auctionId);

}
