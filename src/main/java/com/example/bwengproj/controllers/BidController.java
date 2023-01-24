package com.example.bwengproj.controllers;

import com.example.bwengproj.dto.BidDto;
import com.example.bwengproj.dto.BidItemDto;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.status.BidStatus;
import com.example.bwengproj.model.status.Role;
import com.example.bwengproj.security.JwtTokenUtil;
import com.example.bwengproj.services.BidService;
import com.example.bwengproj.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.bwengproj.util.Util.objectMapper;

@RestController
@RequestMapping("/bids")
public class BidController {
    @Autowired
    private BidService bidService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    @GetMapping("/{id}/all")
    public ResponseEntity<?> getAllByAuction(@PathVariable Long id) throws JsonProcessingException {
        List<Bid> bids = bidService.getAll(id);
        return response(bids);
    }

    @PostMapping
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> create(@RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, JSONException, IllegalAccessException {
        BidDto dto;
        Bid bid = null;
        JSONObject bidJson = new JSONObject(json);
        JSONArray keys = bidJson.names();
        Long userId = null, auctionId = null;
        LocalDateTime deliveryDateTime = null;
        for(int i = 0; i < keys.length(); i++) {
            String key = keys.getString(i);
            JSONObject val = bidJson.getJSONObject(key);
            switch(key) {
                case "userId" -> userId = val.getLong(key);
                case "deliveryDateTime" -> deliveryDateTime = LocalDateTime.parse(val.getString(key), formatter);
                case "auctionId" -> auctionId = val.getLong(key);
                case "items" -> {
                    if(userId != null && deliveryDateTime != null && auctionId != null) {
                        if(tokenMatchesRequest(userId, token)) {
                            dto = new BidDto(userId, deliveryDateTime, auctionId);
                            bid = bidService.create(dto);
                        } else {
                            throw new IllegalAccessException("You cannot create bids on behalf of other users.");
                        }
                    } else {
                        throw new JSONException("JSON could not be processed.");
                    }
                    JSONArray items = bidJson.getJSONArray(key);
                    for(int j = 0; j < items.length(); j++) {
                        String s = items.getString(j);
                        BidItemDto itemDto = objectMapper.readValue(s, BidItemDto.class);
                        bidService.add(bid.getId(), itemDto);
                    }
                }
            }
        }
        return response(bid);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(T(com.example.bwengproj.model.status.Role).ROLE_USER, T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);
        BidDto dto = objectMapper.readValue(json, BidDto.class);

        if(!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(dto.userId(), token)) throw new IllegalAccessException("You cannot update another user's auction.");

        Bid bid = bidService.update(id, dto);
        return response(bid);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);
        Bid b = bidService.get(id);

        if(!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(b.getUser().getId(), token)) throw new IllegalAccessException("You cannot update another user's auction.");

        bidService.delete(id);
        return response(null);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole(T(com.example.bwengproj.model.status.Role).ROLE_USER, T(com.example.bwengproj.model.status.Role).ROLE_ADMIN)")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestParam String status, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);
        Bid b = bidService.get(id);

        if(!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(b.getUser().getId(), token)) throw new IllegalAccessException("You cannot change the status of another user's auction.");

        BidStatus bidStatus = BidStatus.valueOf(status);

        bidService.changeStatus(id, bidStatus);
        return response(null);
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
