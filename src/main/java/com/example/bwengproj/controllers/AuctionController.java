package com.example.bwengproj.controllers;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.security.JwtTokenUtil;
import com.example.bwengproj.services.AuctionService;
import com.example.bwengproj.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static com.example.bwengproj.util.Util.*;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws JsonProcessingException {

        return ResponseEntity.ok(toJson(auctionService.fetchAuctionList()));
    }

    @PostMapping("/new")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> createAuction(@RequestBody String json, @RequestHeader(name = "Authorization") String token) throws JsonProcessingException, JSONException, ParseException {
        token = token.substring(7);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Auction a = auctionService.create(json, username);
        return ResponseEntity.ok(toJson(a));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> updateAuction(@PathVariable Long id, String json, @RequestHeader(name = "Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        Auction aucDB = auctionService.fetchAuctionById(id);
        if(unameMatchesUser(jwtTokenUtil.getUsernameFromToken(token), aucDB.getUser().getUserName())) {
            Auction a = toPojo(json, Auction.class);
            return ResponseEntity.ok(toJson(auctionService.updateAuction(a, id)));
        } else {
            throw new IllegalAccessException("You cannot modify an auction of another user.");
        }
    }

    private void changeAuctionState(Long id) {

    }

    private boolean idMatchesUser(Long id, String token) {
        token = token.substring(7);
        return Objects.equals(userService.fetchUserByUsername(jwtTokenUtil.getUsernameFromToken(token)).getId(), id);
    }

    private boolean unameMatchesUser(String uname, String token) {
        token = token.substring(7);
        return Objects.equals(jwtTokenUtil.getUsernameFromToken(token), uname);
    }
}
