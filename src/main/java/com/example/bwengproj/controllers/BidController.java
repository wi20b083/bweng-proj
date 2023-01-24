package com.example.bwengproj.controllers;

import com.example.bwengproj.services.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bids")
public class BidController {
    @Autowired
    private BidService bidService;

    @GetMapping("/all")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    public ResponseEntity<?> getAllBids() {
        return null;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> getBidById(@PathVariable Long id) {
        return null;
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> createBid(String json) {
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN) or hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> deleteBid(@PathVariable Long id) {
        return null;
    }
}
