package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Objects;

public class AuctionServiceImpl implements AuctionService{

    @Autowired
    private AuctionRepository auctionRepository;

    // save operation
    @Override
    public Auction saveAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    // read operation
    @Override
    public List<Auction> fetchAuctionList(){
        return (List<Auction>) auctionRepository.findAll();
    }

    // update operation
    @Override
    public Auction updateAuction(Auction auction, Long auctionId) {
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

    @Override
    public List<Auction> fetchAuctionByUserId(Long userId) {

        return null;
    }

    // delete operation
    @Override
    public void deleteAuctionById(Long auctionId) {
        auctionRepository.deleteById(auctionId);
    }
}
