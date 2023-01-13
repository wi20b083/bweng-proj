package com.example.bwengproj.controllers;

import com.example.bwengproj.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @PostMapping("/new")
    public ResponseEntity<?> createAuction(String json) {
        return null;
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuction(@PathVariable Long id, @RequestParam(name = "state", required = false) Optional<Boolean> state, String json) {
        return null;
    }

    private void changeAuctionState(Long id) {

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuction(@PathVariable Long id) {
        return null;
    }
}
