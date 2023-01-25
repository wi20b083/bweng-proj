package com.example.bwengproj.controllers;

import com.example.bwengproj.dto.AuctionDto;
import com.example.bwengproj.dto.AuctionItemDto;
import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.AuctionStatus;
import com.example.bwengproj.model.status.Role;
import com.example.bwengproj.security.JwtTokenUtil;
import com.example.bwengproj.services.AuctionService;
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
@RequestMapping("/auctions")
public class AuctionController {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    @Autowired
    private AuctionService auctionService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * Gets all {@link Auction} entities from the Database,
     * Authentication: none,
     * Authorization: all
     *
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the found {@link Auction} entities attached
     */
    @GetMapping("/all")
    public ResponseEntity<?> getAll() throws JsonProcessingException {
        List<Auction> auctions = auctionService.getAll();
        return response(auctions);
    }

    /**
     * Creates an {@link Auction} entity in the Database,
     * Authentication: authenticated,
     * Authorization: {@link Role} "ROLE_USER"
     *
     * @param json  JSON String with {@link AuctionDto} fields and a nested array of JSON Objects with {@link AuctionItemDto} fields
     * @param token JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the created {@link Auction} entity attached
     * @throws JSONException          The provided JSON String could not be read
     * @throws IllegalAccessException The requesting {@link User} tried to create an {@link Auction} entity on behalf of another {@link User}
     */
    @PostMapping
    @PreAuthorize("hasRole(T(com.example.bwengproj.model.status.Role).ROLE_USER)")
    public ResponseEntity<?> create(@RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, JSONException, IllegalAccessException {
        AuctionDto dto;
        Auction auction = null;
        JSONObject auctionJson = new JSONObject(json);
        JSONArray keys = auctionJson.names();
        Long userId = null;
        LocalDateTime startDateTime = null, deliveryDateTime = null, endDateTime = null;
        for (int i = 0; i < keys.length(); i++) {
            String key = keys.getString(i);
            JSONObject val = auctionJson.getJSONObject(key);
            switch (key) {
                case "userId" -> userId = val.getLong(key);
                case "startDateTime" -> startDateTime = LocalDateTime.parse(val.getString(key), formatter);
                case "deliveryDateTime" -> deliveryDateTime = LocalDateTime.parse(val.getString(key), formatter);
                case "endDateTime" -> endDateTime = LocalDateTime.parse(val.getString(key), formatter);
                case "items" -> {
                    if (userId != null && startDateTime != null && deliveryDateTime != null && endDateTime != null) {
                        if (tokenMatchesRequest(userId, token, jwtTokenUtil, userService)) {
                            dto = new AuctionDto(userId, startDateTime, deliveryDateTime, endDateTime);
                            auction = auctionService.create(dto);
                        } else {
                            throw new IllegalAccessException("You cannot create auctions on behalf of other users.");
                        }
                    } else {
                        throw new JSONException("JSON could not be processed.");
                    }
                    JSONArray items = auctionJson.getJSONArray(key);
                    for (int j = 0; j < items.length(); j++) {
                        String s = items.getString(j);
                        AuctionItemDto itemDto = objectMapper.readValue(s, AuctionItemDto.class);
                        auctionService.add(auction.getId(), itemDto);
                    }
                }
            }
        }
        return response(auction);
    }

    /**
     * Updates an {@link Auction} entity from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @param id    ID of the {@link Auction} to update
     * @param json  JSON String with {@link AuctionDto} fields
     * @param token JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200 and the updated {@link Auction} entity attached
     * @throws IllegalAccessException The requesting {@link User} tried to update an {@link Auction} entity that belongs to another {@link User}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody String json, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);
        AuctionDto dto = objectMapper.readValue(json, AuctionDto.class);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(dto.userId(), token, jwtTokenUtil, userService))
            throw new IllegalAccessException("You cannot update another user's auction.");

        Auction auction = auctionService.update(id, dto);
        return response(auction);
    }

    /**
     * Deletes an {@link Auction} entity from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @param id    ID of the {@link Auction} to delete
     * @param token JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200
     * @throws IllegalAccessException The requesting {@link User} tried to delete an {@link Auction} entity that belongs to another {@link User}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);
        Auction a = auctionService.get(id);
        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(a.getUser().getId(), token, jwtTokenUtil, userService))
            throw new IllegalAccessException("You cannot delete another user's auction.");

        auctionService.delete(id);
        return response(null);
    }

    /**
     * Changes the status of an {@link Auction} entity from the Database,
     * Authentication: authenticated,
     * Authorization: all
     *
     * @param id     ID of the {@link Auction} to change the status of
     * @param status Status to set
     * @param token  JWT
     * @return {@link ResponseEntity} with {@link HttpStatus} 200
     * @throws IllegalAccessException The requesting {@link User} tried to change the status of an {@link Auction} entity that belongs to another {@link User} or
     *                                the requesting {@link User} has no permission to set the desired {@link AuctionStatus}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable Long id, @RequestParam String status, @RequestHeader("Authorization") String token) throws JsonProcessingException, IllegalAccessException {
        List<Role> roles = jwtTokenUtil.getRolesFromToken(token);
        Auction a = auctionService.get(id);

        if (!roles.contains(Role.ROLE_ADMIN) && !tokenMatchesRequest(a.getUser().getId(), token, jwtTokenUtil, userService))
            throw new IllegalAccessException("You cannot change the status of another user's auction.");

        AuctionStatus auctionStatus = AuctionStatus.valueOf(status);

        if (!roles.contains(Role.ROLE_ADMIN) && auctionStatus.equals(AuctionStatus.AUCTION_LOCKED))
            throw new IllegalAccessException("You have no permission to change the auction status to " + auctionStatus.name());

        auctionService.changeStatus(id, auctionStatus);
        return response(null);
    }
}
