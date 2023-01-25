package com.example.bwengproj.model;

import com.example.bwengproj.model.status.BidStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    @OneToMany(mappedBy = "bid", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<BidItem> items = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @Column(name = "delivery_date_time", columnDefinition = "TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime deliveryDateTime;

    @Enumerated(EnumType.STRING)
    private BidStatus status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    public void addItem(BidItem item) {
        items.add(item);
        item.setBid(this);
    }

    public void removeItem(BidItem item) {
        items.remove(item);
        item.setBid(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Bid)) return false;
        return id != null && id.equals(((Bid) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}