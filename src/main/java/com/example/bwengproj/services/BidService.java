package com.example.bwengproj.services;

import com.example.bwengproj.model.Bid;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
public interface BidService {
    // save operation
    Bid saveBid(@Valid Bid bid);

    // read operation
    Iterable<Bid> fetchBidList();

    // update operation
    Bid updateBid(@Valid Bid bid, Long bidId);

    // delete operation
    void deleteBidById(Long bidId);

    // find by id
    Bid fetchBidById(Long bidId);
}
