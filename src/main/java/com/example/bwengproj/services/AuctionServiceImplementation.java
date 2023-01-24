package com.example.bwengproj.services;

import com.example.bwengproj.dto.AuctionDto;
import com.example.bwengproj.dto.AuctionItemDto;
import com.example.bwengproj.model.Auction;
import com.example.bwengproj.model.AuctionItem;
import com.example.bwengproj.model.Product;
import com.example.bwengproj.model.User;
import com.example.bwengproj.model.status.AuctionStatus;
import com.example.bwengproj.repository.AuctionRepository;
import com.example.bwengproj.repository.ProductRepository;
import com.example.bwengproj.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionServiceImplementation implements AuctionService {

    @Autowired
    private AuctionRepository auctionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Auction save(Auction auction) {
        return auctionRepository.save(auction);
    }

    @Override
    public Auction create(AuctionDto dto) {
        Auction db = new Auction();
        Optional<User> u = userRepository.findById(dto.userId());
        if(u.isPresent()) {
            User user = u.get();
            db.setUser(user);
            db.setStartDateTime(dto.startDateTime());
            db.setDeliveryDateTime(dto.deliveryDateTime());
            db.setEndDateTime(dto.endDateTime());
            db.setStatus(AuctionStatus.AUCTION_OPEN);
            return save(db);
        } else {
            throw new EntityNotFoundException("No user found with ID " + dto.userId());
        }
    }

    @Override
    public List<Auction> getAll() {
        return auctionRepository.findAll();
    }

    @Override
    public List<Auction> getAll(User user) {
        return auctionRepository.getAuctionsByUser(user);
    }

    @Override
    public Auction get(Long id) {
        Optional<Auction> a = auctionRepository.findById(id);
        if(a.isPresent()) {
            return a.get();
        } else {
            throw new EntityNotFoundException("No auction found with ID " + id);
        }
    }

    @Override
    public Auction update(Long id, AuctionDto dto) {
        Auction db = get(id);
        db.setStartDateTime(dto.startDateTime());
        db.setDeliveryDateTime(dto.deliveryDateTime());
        db.setEndDateTime(dto.endDateTime());
        return auctionRepository.save(db);
    }

    @Override
    public void delete(Long id) {
        auctionRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        auctionRepository.deleteAll();
    }

    @Override
    public Auction add(Long auctionId, AuctionItemDto dto) {
        Auction a = get(auctionId);
        Product p;
        Optional<Product> o = productRepository.findById(dto.productId());
        if(o.isPresent()) {
            p = o.get();
        } else {
            throw new EntityNotFoundException("Could not find product with ID " + dto.productId());
        }
        AuctionItem item = new AuctionItem();
        item.setAuction(a);
        item.setProduct(p);
        item.setAmount(dto.amount());
        item.setCostPerUnit(dto.costPerUnit());
        a.addItem(item);
        return save(a);
    }

    @Override
    public void changeStatus(Long id, AuctionStatus status) {
        Auction db = get(id);
        db.setStatus(status);
        save(db);
    }
}
