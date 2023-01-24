package com.example.bwengproj.services;

import com.example.bwengproj.dto.BidDto;
import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.Bid;
import com.example.bwengproj.repository.BidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BidServiceImplementation implements BidService {
    @Autowired
    private BidRepository bidRepository;

    @Override
    public Bid save(Bid bid) {
        return bidRepository.save(bid);
    }

    @Override
    public List<Bid> getAll(Auction auction) {
        return bidRepository.getBidByAuction(auction);
    }

    @Override
    public Bid get(Long id) {
        Optional<Bid> b = bidRepository.findById(id);
        if(b.isPresent()) {
            return b.get();
        } else {
            throw new EntityNotFoundException("No bid found with ID " + id);
        }
    }

    @Override
    public Bid update(Long id, BidDto dto) {
        Bid db = get(id);
        db.setDeliveryDateTime(dto.deliveryDateTime());
        return bidRepository.save(db);
    }

    @Override
    public void delete(Long id) {
        bidRepository.deleteById(id);
    }

    @Override
    public void delete(Auction auction) {
        bidRepository.deleteBidsByAuction(auction);
    }

    @Override
    public void deleteAll() {
        bidRepository.deleteAll();
    }
}
