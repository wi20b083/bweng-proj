package com.example.bwengproj.controllers;

import com.example.bwengproj.services.AuctionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @GetMapping("/all")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.Role).ROLE_USER)")
    public ResponseEntity<?> getAll() {
        return null;
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.Role).ROLE_USER)")
    public ResponseEntity<?> createAuction(String json) {
        return null;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.Role).ROLE_USER)")
    public ResponseEntity<?> updateAuction(@PathVariable Long id, @RequestParam(name = "state", required = false) Optional<Boolean> state, String json) {
        return null;
    }

    private void changeAuctionState(Long id) {

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.Role).ROLE_USER)")
    public ResponseEntity<?> deleteAuction(@PathVariable Long id) {
        return null;
    }
}
