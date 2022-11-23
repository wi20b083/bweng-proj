package com.example.bwengproj.services;

import com.example.bwengproj.model.Bid;
import com.example.bwengproj.model.Product;
import com.example.bwengproj.repository.BidRepository;
import com.example.bwengproj.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class BidServiceImpl implements BidService{
    @Autowired
    private BidRepository bidRepository;

    // save operation
    @Override
    public Bid saveBid(Bid bid) {
        return bidRepository.save(bid);
    }

    // read operation
    @Override
    public List<Bid> fetchBidList(){
        return (List<Bid>) bidRepository.findAll();
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
    public Bid updateBid(Bid bid, Long bidId) {
        Bid bidDB = bidRepository.findById(bidId).get();

        // insert code with finished methods from Auction in model

        return null;
    }

    // delete operation
    @Override
    public void deleteBidById(Long bidId) {
        bidRepository.deleteById(bidId);
    }
}
