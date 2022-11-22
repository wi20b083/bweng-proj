package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;

import java.util.List;

public interface AuctionService {

    // save operation
    Auction saveAuction(Auction auction);

    // read operation
    List<Auction> fetchAuctionList();

    // update operation
    Auction updateAuction(Auction auction, Integer auctionId);


    // delete operation
    void deleteAuctionById(Integer auctionId);


}
