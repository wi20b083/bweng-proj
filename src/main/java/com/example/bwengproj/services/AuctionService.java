package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import org.springframework.boot.configurationprocessor.json.JSONException;

import javax.validation.Valid;
import java.text.ParseException;
import java.util.List;

public interface AuctionService {

    // save operation
    Auction saveAuction(Auction auction);

    Auction create(String json, String username) throws JSONException, ParseException;

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
