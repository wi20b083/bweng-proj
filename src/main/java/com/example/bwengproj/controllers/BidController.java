package com.example.bwengproj.controllers;

import com.example.bwengproj.dto.BidDto;
import com.example.bwengproj.dto.BidItemDto;
import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.User;
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

import static com.example.bwengproj.util.Util.*;

@RestController
@RequestMapping("/bids")
public class BidController {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    @Autowired
    private BidService bidService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Gets all {@link Bid} entities associated to the given {@link Auction}'s ID from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @param id ID of the {@link Auction}
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the found {@link Auction} entities attached
     */
    @GetMapping("/{id}/all")
    public ResponseEntity<?> getAllByAuction(@PathVariable Long id) throws JsonProcessingException {
        List<Bid> bids = bidService.getAll(id);
        return response(bids);
    }

    /**
     * Creates a {@link Bid} entity in the Database,
     * Authentication: authenticated,
     * Authorization: {@link Role} "ROLE_USER"
     *
     * @param json  JSON String with {@link BidDto} fields and a nested array of JSON Objects with {@link BidItemDto} fields
     * @param token JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the created {@link Bid} entity attached
     * @throws JSONException          The provided JSON String could not be read
     * @throws IllegalAccessException The requesting {@link User} tried to create an {@link Bid} entity on behalf of another {@link User}
     */
    @PostMapping
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> create(@RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, JSONException, IllegalAccessException {
        BidDto dto;
        Bid bid = null;
        JSONObject bidJson = new JSONObject(json);
        JSONArray keys = bidJson.names();
        Long userId = null, auctionId = null;
        LocalDateTime deliveryDateTime = null;
        for (int i = 0; i < keys.length(); i++) {
            String key = keys.getString(i);
            JSONObject val = bidJson.getJSONObject(key);
            switch (key) {
                case "userId" -> userId = val.getLong(key);
                case "deliveryDateTime" -> deliveryDateTime = LocalDateTime.parse(val.getString(key), formatter);
                case "auctionId" -> auctionId = val.getLong(key);
                case "items" -> {
                    if (userId != null && deliveryDateTime != null && auctionId != null) {
                        if (tokenMatchesRequest(userId, token, jwtTokenUtil, userService)) {
                            dto = new BidDto(userId, deliveryDateTime, auctionId);
                            bid = bidService.create(dto);
                        } else {
                            throw new IllegalAccessException("You cannot create bids on behalf of other users.");
                        }
                    } else {
                        throw new JSONException("JSON could not be processed.");
                    }
                    JSONArray items = bidJson.getJSONArray(key);
                    for (int j = 0; j < items.length(); j++) {
                        String s = items.getString(j);
                        BidItemDto itemDto = objectMapper.readValue(s, BidItemDto.class);
                        bidService.add(bid.getId(), itemDto);
                    }
                }
            }
        }
        return response(bid);
    }

    /**
     * Updates a {@link Bid} entity from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @param id    ID of the {@link Bid} to update
     * @param json  JSON String with {@link BidDto} fields
     * @param token JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the updated {@link Bid} entity attached
     * @throws IllegalAccessException The requesting {@link User} tried to update an {@link Bid} entity that belongs to another {@link User}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);
        BidDto dto = objectMapper.readValue(json, BidDto.class);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(dto.userId(), token, jwtTokenUtil, userService))
            throw new IllegalAccessException("You cannot update another user's auction.");

        Bid bid = bidService.update(id, dto);
        return response(bid);
    }

    /**
     * Deletes a {@link Bid} entity from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @param id    ID of the {@link Bid} to delete
     * @param token JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200
     * @throws IllegalAccessException The requesting {@link User} tried to delete an {@link Bid} entity that belongs to another {@link User}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);
        Bid b = bidService.get(id);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(b.getUser().getId(), token, jwtTokenUtil, userService))
            throw new IllegalAccessException("You cannot update another user's auction.");

        bidService.delete(id);
        return response(null);
    }

    /**
     * Changes the status of an {@link Bid} entity from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @param id     ID of the {@link Bid} to change the status of
     * @param status Status to set
     * @param token  JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200
     * @throws IllegalAccessException The requesting {@link User} tried to change the status of an {@link Bid} entity that belongs to another {@link User} or
     *                                the requesting {@link User} has no permission to set the desired {@link BidStatus}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestParam String status, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);
        Bid b = bidService.get(id);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(b.getUser().getId(), token, jwtTokenUtil, userService))
            throw new IllegalAccessException("You cannot change the status of another user's auction.");

        BidStatus bidStatus = BidStatus.valueOf(status);

        bidService.changeStatus(id, bidStatus);
        return response(null);
    }

}
