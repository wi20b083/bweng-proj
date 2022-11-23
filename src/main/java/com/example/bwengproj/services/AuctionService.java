package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import org.springframework.stereotype.Service;

@Service
public interface AuctionService {

    // save operation
    Auction saveAuction(Auction auction);

    // read operation
    Iterable<Auction> fetchAuctionList();

    // find by id
    Auction fetchAuctionById(Long auctionId);

    // update operation
    Auction updateAuction(Auction auction, Long auctionId);


    // delete operation
    void deleteAuctionById(Long auctionId);


}
