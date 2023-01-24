package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.AuctionItem;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.Product;
import com.example.bwengproj.repository.AuctionRepository;
import com.example.bwengproj.repository.ProductRepository;
import com.example.bwengproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AuctionServiceImpl implements AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // save operation
    @Override
    public Auction saveAuction(@Valid Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public Auction create(String json, String username) throws JSONException, ParseException {
        JSONObject o = new JSONObject(json);
        JSONArray keys = o.names();
        Auction a = new Auction();
        for(int i = 0; i < keys.length(); i++) {
            String key = keys.getString(i);
            String val = o.getString(key);
            switch (key) {
                case "startDate" -> a.setStartDate(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(val));
                case "endDate" -> a.setEndDate(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(val));
                case "deliveryDate" -> a.setDeliveryDate(new SimpleDateFormat("dd.MM.yyyy HH:mm").parse(val));
                case "items" -> {
                    JSONArray arr = o.getJSONArray(key);
                    for(int j = 0; j < arr.length(); j++) {
                        JSONObject obj = arr.getJSONObject(j);
                        Product p = productRepository.findById(obj.getLong("id")).get();
                        AuctionItem item = new AuctionItem();
                        item.setProduct(p);
                        item.setAmount(obj.getInt("amount"));
                        item.setPrice(obj.getInt("price"));
                        a.getItems().add(item);
                    }
                }
                case "categories" -> {
                    JSONArray arr = o.getJSONArray(key);
                    for(int j = 0; j < arr.length(); j++) {
                        JSONObject obj = arr.getJSONObject(j);
                        String category = obj.getString("name");
                        a.getCategories().add(category);
                    }
                }
            }
        }
        a.setUser(userRepository.findByUserName(username).get());
        return auctionRepository.save(a);
    }

    // read operation
    @Override
    public Iterable<Auction> fetchAuctionList() {
        return auctionRepository.findAll();
    }

    @Override
    public Auction fetchAuctionById(Long auctionId) {
        return auctionRepository.findById(auctionId).get();
    }

    // update operation
    @Override
    public Auction updateAuction(@Valid Auction auction, Long auctionId) {
        Optional<Auction> aucDB = auctionRepository.findById(auctionId);
        if(aucDB.isPresent()) {
            Auction a = aucDB.get();
            a.setUser(auction.getUser());
            a.setItems(auction.getItems());
            a.setStartDate(auction.getStartDate());
            a.setDeliveryDate(auction.getDeliveryDate());
            a.setEndDate(auction.getEndDate());
            a.setCategories(auction.getCategories());
            return auctionRepository.save(a);
        } else {
            throw new EntityNotFoundException("No auction found with id " + auctionId);
        }
    }

    // delete operation
    @Override
    public void deleteAuctionById(Long auctionId) {
        auctionRepository.deleteById(auctionId);
    }

    @Override
    public Auction addBidToAuctionById(@Valid Bid bid, Long auctionId) {
        Auction aucDB = fetchAuctionById(auctionId);
        aucDB.getBids().add(bid);
        return auctionRepository.save(aucDB);
    }

}
