package com.example.bwengproj.services;

import com.example.bwengproj.dto.BidDto;
import com.example.bwengproj.dto.BidItemDto;
import com.example.bwengproj.model.*;
import com.example.bwengproj.model.status.BidStatus;
import com.example.bwengproj.repository.AuctionRepository;
import com.example.bwengproj.repository.BidRepository;
import com.example.bwengproj.repository.ProductRepository;
import com.example.bwengproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class BidServiceImplementation implements BidService {
    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Bid save(Bid bid) {
        return bidRepository.save(bid);
    }

    @Override
    public Bid create(BidDto dto) {
        Bid db = new Bid();
        Optional<User> u = userRepository.findById(dto.userId());
        if(u.isPresent()) {
            User user = u.get();
            db.setUser(user);
            db.setDeliveryDateTime(dto.deliveryDateTime());
            db.setStatus(BidStatus.BID_PENDING);
            return save(db);
        } else {
            throw new EntityNotFoundException("No user found with ID " + dto.userId());
        }
    }

    @Override
    public List<Bid> getAll(Long auctionId) {
        Optional<Auction> o = auctionRepository.findById(auctionId);
        if(o.isEmpty()) throw new EntityNotFoundException("No auction found with ID " + auctionId);
        Auction a = o.get();
        return bidRepository.getBidByAuction(a);
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

    @Override
    public Bid add(Long bidId, BidItemDto dto) {
        Bid b = get(bidId);
        Product p;
        Optional<Product> o = productRepository.findById(dto.productId());
        if(o.isPresent()) {
            p = o.get();
        } else {
            throw new EntityNotFoundException("Could not find product with ID " + dto.productId());
        }
        BidItem item = new BidItem();
        item.setBid(b);
        item.setProduct(p);
        item.setAmount(dto.amount());
        item.setCostPerUnit(dto.costPerUnit());
        b.addItem(item);
        return save(b);    }

    @Override
    public void changeStatus(Long id, BidStatus status) {
        Bid db = get(id);
        db.setStatus(status);
        save(db);
    }
}
