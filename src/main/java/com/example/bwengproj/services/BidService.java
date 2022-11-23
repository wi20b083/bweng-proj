package com.example.bwengproj.services;

import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.Product;

import java.util.List;

public interface BidService {
    // save operation
    Bid saveBid(Bid bid);

    // read operation
    List<Bid> fetchBidList();

    // update operation
    Bid updateBid(Bid bid, Long bidId);

    // delete operation
    void deleteBidById(Long bidId);

    // find by id
    Bid fetchBidById(Long bidId);
}
