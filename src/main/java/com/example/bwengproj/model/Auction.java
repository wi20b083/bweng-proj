package com.example.bwengproj.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private Date startDate;
    private Date endDate;
    private boolean status;
    private Date deliveryDate;
    private int total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    private Set<AuctionItem> items;
}
