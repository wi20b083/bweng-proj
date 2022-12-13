package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Objects;

@Service
public class AuctionServiceImpl implements AuctionService{

    @Autowired
    private AuctionRepository auctionRepository;

    // save operation
    @Override
    public Auction saveAuction(@Valid Auction auction) {
        return auctionRepository.save(auction);
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
        Auction aucDB = auctionRepository.findById(auctionId).get();

        if (Objects.nonNull(auction.getStartDate()) && !aucDB.getStartDate().equals(auction.getStartDate())) {
            aucDB.setStartDate(auction.getStartDate());
        }

        if (Objects.nonNull(auction.getEndDate()) && !aucDB.getEndDate().equals(auction.getEndDate())) {
            aucDB.setEndDate(auction.getEndDate());
        }

        if (Objects.nonNull(auction.getDeliveryDate()) && !aucDB.getDeliveryDate().equals(auction.getDeliveryDate())) {
            aucDB.setDeliveryDate(auction.getDeliveryDate());
        }

        if (auction.isStatus() != aucDB.isStatus()) {
            aucDB.setStatus(auction.isStatus());
        }

        return auctionRepository.save(aucDB);
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
