package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public interface AuctionService {

    // save operation
    Auction saveAuction(@Valid Auction auction);

    // read operation
    Iterable<Auction> fetchAuctionList();

    // find by id
    Auction fetchAuctionById(Long auctionId);

    // update operation
    Auction updateAuction(@Valid Auction auction, Long auctionId);


    // delete operation
    void deleteAuctionById(Long auctionId);


}
