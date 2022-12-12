package com.example.bwengproj.controllers;

import com.example.bwengproj.model.Bid;
import com.example.bwengproj.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class BidController {

    @Autowired
    private BidService bidService;

    // /bids
    // Save operation
    @PostMapping("/bids")
    public Bid saveBid(@Valid @RequestBody Bid bid){

        return bidService.saveBid(bid);
    }

    // Read operation
    @GetMapping("/bids")
    public Iterable<Bid> fetchBidList(){
        return bidService.fetchBidList();
    }

    // Update operation
    @PutMapping("/bids/{id}")
    public Bid updateBid(@Valid @RequestBody Bid bid, @PathVariable("id") long bidId) {
        return bidService.updateBid(bid, bidId);
    }

    // Delete operation
    @DeleteMapping("/bids/{id}")
    public String deleteBidById(@PathVariable("id") long bidId){
        bidService.deleteBidById(bidId);
        return "Deleted Successfully";
    }

    // get by id
    @GetMapping("/bids/{id}")
    public Bid fetchBidByID(@PathVariable("id") long bidId){
        return bidService.fetchBidById(bidId);
    }
}
