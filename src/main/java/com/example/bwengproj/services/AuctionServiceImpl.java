package com.example.bwengproj.services;

import com.example.bwengproj.model.Auction;
import com.example.bwengproj.repository.AuctionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    /* update operation
    @Override
    public Auction updateAuction(Auction auction, Long auctionId) {
        Auction aucDB = auctionRepository.findById(auctionId).get();

        if (Objects.nonNull(department.getDepartmentName()) && !"".equalsIgnoreCase(department.getDepartmentName())) {
            depDB.setDepartmentName(department.getDepartmentName());
        }

        if (Objects.nonNull(department.getDepartmentAddress()) && !"".equalsIgnoreCase(department.getDepartmentAddress())) {
            depDB.setDepartmentAddress(department.getDepartmentAddress());
        }

        if (Objects.nonNull(department.getDepartmentCode()) && !"".equalsIgnoreCase(department.getDepartmentCode())) {
            depDB.setDepartmentCode(department.getDepartmentCode());
        }

        return departmentRepository.save(depDB);
    }*/

    @Override
    public Auction updateAuction(Auction auction, Integer auctionId) {
        Auction auctionDB = auctionRepository.findById(auctionId).get();

        // insert code with finished methods from Auction in model

        return null;
    }

    // delete operation
    @Override
    public void deleteAuctionById(Integer auctionId) {
        auctionRepository.deleteById(auctionId);
    }
}
