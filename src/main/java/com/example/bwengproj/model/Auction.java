package com.example.bwengproj.model;

import com.example.bwengproj.model.status.AuctionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "auction")
@Getter
@Setter
public class Auction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "start_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime startDateTime;

    @Column(name = "delivery_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime deliveryDateTime;

    @Column(name = "end_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime endDateTime;

    @Enumerated(EnumType.STRING)
    private AuctionStatus status;

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AuctionItem> items;

    public void addItem(AuctionItem item) {
        items.add(item);
        item.setAuction(this);
    }

    public void removeItem(AuctionItem item) {
        items.remove(item);
        item.setAuction(null);
    }

    @OneToMany(mappedBy = "auction", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bid> bids;

    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setAuction(this);
    }

    public void removeBid(Bid bid) {
        bids.remove(bid);
        bid.setAuction(null);
    }

    //TODO: private Set<String> categories;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Auction)) return false;
        return id != null && id.equals(((Auction) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}