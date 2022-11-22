package com.example.bwengproj.repository;

import com.example.bwengproj.model.Auction;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionRepository extends CrudRepository<Auction, Integer> {

}