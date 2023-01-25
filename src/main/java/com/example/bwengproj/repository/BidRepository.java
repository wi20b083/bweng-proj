package com.example.bwengproj.repository;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> getBidByAuction(Auction auction);

    void deleteBidsByAuction(Auction auction);
}
