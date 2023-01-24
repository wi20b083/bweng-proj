package com.example.bwengproj.controllers;

import com.example.bwengproj.dto.AuctionDto;
import com.example.bwengproj.dto.AuctionItemDto;
import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.AuctionItem;
import com.example.bwengproj.model.User;
import com.example.bwengproj.security.JwtTokenUtil;
import com.example.bwengproj.services.AuctionService;
import com.example.bwengproj.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.boot.json.JsonParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.bwengproj.util.Util.objectMapper;

@RestController
@RequestMapping("/auctions")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    @GetMapping("/all")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    public ResponseEntity<?> getAll() throws JsonProcessingException {
        List<Auction> auctions = auctionService.getAll();
        return response(auctions);
    }

    @PostMapping
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> create(@RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, JSONException, IllegalAccessException {
        AuctionDto dto;
        Auction auction = null;
        JSONObject auctionJson = new JSONObject(json);
        JSONArray keys = auctionJson.names();
        Long userId = null;
        LocalDateTime startDateTime = null, deliveryDateTime = null, endDateTime = null;
        for(int i = 0; i < keys.length(); i++) {
            String key = keys.getString(i);
            JSONObject val = auctionJson.getJSONObject(key);
            switch(key) {
                case "userId" -> userId = val.getLong(key);
                case "startDateTime" -> startDateTime = LocalDateTime.parse(val.getString(key), formatter);
                case "deliveryDateTime" -> deliveryDateTime = LocalDateTime.parse(val.getString(key), formatter);
                case "endDateTime" -> endDateTime = LocalDateTime.parse(val.getString(key), formatter);
                case "items" -> {
                    if(userId != null && startDateTime != null && deliveryDateTime != null && endDateTime != null) {
                        if(tokenMatchesRequest(userId, token)) {
                            dto = new AuctionDto(userId, startDateTime, deliveryDateTime, endDateTime);
                            auction = auctionService.create(dto);
                        } else {
                            throw new IllegalAccessException("You cannot create auctions on behalf of other users.");
                        }
                    } else {
                        throw new JSONException("JSON could not be processed.");
                    }
                    JSONArray items = auctionJson.getJSONArray(key);
                    for(int j = 0; j < items.length(); j++) {
                        String s = items.getString(j);
                        AuctionItemDto itemDto = objectMapper.readValue(s, AuctionItemDto.class);
                        auctionService.add(auction.getId(), itemDto);
                    }
                }
            }
        }
        return response(auction);
    }

    //not authenticated
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) throws JsonProcessingException {
        Auction auction = auctionService.get(id);
        return response(auction);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        AuctionDto dto = objectMapper.readValue(json, AuctionDto.class);
        if(tokenMatchesRequest(dto.userId(), token)) {
            Auction auction = auctionService.update(id, dto);
            return response(auction);
        } else {
            throw new IllegalAccessException("You cannot update another user's auctions");
        }
    }

    private ResponseEntity<?> response(Object o) throws JsonProcessingException {
        return new ResponseEntity<>(objectMapper.writeValueAsString(o), HttpStatus.OK);
    }

    private Boolean tokenMatchesRequest(Long id, String token) {
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Long userId = userService.get(username).getId();
        return userId.longValue() == id.longValue();
    }
}
