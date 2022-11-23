package com.example.bwengproj.services;

import com.example.bwengproj.model.Bid;

public interface BidService {
    // save operation
    Bid saveBid(Bid bid);

    // read operation
    Iterable<Bid> fetchBidList();

    // update operation
    Bid updateBid(Bid bid, Long bidId);

    // delete operation
    void deleteBidById(Long bidId);

    // find by id
    Bid fetchBidById(Long bidId);
}
