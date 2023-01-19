package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;

import javax.validation.Valid;

public interface AuctionService {

    // save operation
    Auction saveAuction(Auction auction);

    // read operation
    Iterable<Auction> fetchAuctionList();

    // find by id
    Auction fetchAuctionById(Long auctionId);

    // update operation
    Auction updateAuction(@Valid Auction auction, Long auctionId);


    // delete operation
    void deleteAuctionById(Long auctionId);

    Auction addBidToAuctionById(@Valid Bid bid, Long auctionId);


}
