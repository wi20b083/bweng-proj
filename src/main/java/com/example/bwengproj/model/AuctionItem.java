package com.example.bwengproj.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Positive;

@Entity
@Table(name = "auction_item")
@Getter
@Setter
public class AuctionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Auction auction;

    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @Positive
    @Column(name = "amount")
    private Integer amount;

    @Positive
    @Column(name = "cost_per_unit")
    private Integer costPerUnit;


    @Positive
    @Column(name = "total")
    private Integer total;

    @PrePersist
    private void total() {
        this.total = this.amount * this.costPerUnit;
    }
}