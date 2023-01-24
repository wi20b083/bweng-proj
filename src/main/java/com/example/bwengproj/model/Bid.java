package com.example.bwengproj.model;

import com.example.bwengproj.model.status.BidStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "bid")
@Getter
@Setter
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Column(name = "delivery_date_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime deliveryDateTime;

    @Enumerated(EnumType.STRING)
    private BidStatus status;

    //Auction auction

    @OneToMany(mappedBy = "bid", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<BidItem> items = new HashSet<>();

    public void addItem(BidItem item) {
        items.add(item);
        item.setBid(this);
    }

    public void removeItem(BidItem item) {
        items.remove(item);
        item.setBid(null);
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if(!(o instanceof Bid)) return false;
        return id != null && id.equals(((Bid) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}