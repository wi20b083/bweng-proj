package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;

import java.util.List;

public interface AuctionService {

    // save operation
    Auction saveAuction(Auction auction);

    // read operation
    List<Auction> fetchAuctionList();

    // find by id
    Auction fetchAuctionById(Long auctionId);

    // find by userId
    List<Auction> fetchAuctionByUserId(Long userId);

    // update operation
    Auction updateAuction(Auction auction, Long auctionId);


    // delete operation
    void deleteAuctionById(Long auctionId);


}
