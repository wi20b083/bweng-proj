package com.example.bwengproj.controllers;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    // /auctions
    // Save operation
    @PostMapping("/auctions")
    public Auction saveAuction(@Valid @RequestBody Auction auction) {
        return auctionService.saveAuction(auction);
    }

    // Read operation
    @GetMapping("/auctions")
    public Iterable<Auction> fetchAuctionList() {
        return auctionService.fetchAuctionList();
    }

    // Update operation
    @PutMapping("/auctions/{id}")
    public Auction updateAuction(@Valid @RequestBody Auction auction, @PathVariable("id") long auctionId) {
        return auctionService.updateAuction(auction, auctionId);
    }

    // Delete operation
    @DeleteMapping("/auctions/{id}")
    public String deleteAuctionById(@PathVariable("id") long auctionId) {
        auctionService.deleteAuctionById(auctionId);
        return "Deleted Successfully";
    }

    // get auction by id
    @GetMapping("/auctions/{id}")
    public Auction fetchAuctionByID(@PathVariable("id") long auctionId) {
        return auctionService.fetchAuctionById(auctionId);
    }
}
