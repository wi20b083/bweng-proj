package com.example.bwengproj.controllers;

import com.example.bwengproj.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bids")
public class BidController {
    @Autowired
    private BidService bidService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllBids() {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBidById(@PathVariable Long id) {
        return null;
    }

    @PutMapping("/new")
    public ResponseEntity<?> createBid(String json) {
        return null;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBid(@PathVariable Long id) {
        return null;
    }
}
