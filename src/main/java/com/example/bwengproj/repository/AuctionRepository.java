package com.example.bwengproj.repository;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    List<Auction>getAuctionsByUser(User user);
}
