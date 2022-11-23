package com.example.bwengproj.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startDate;
    private Date endDate;
    private boolean status;
    private Date deliveryDate;
    private int total;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    private int getTotal() {
        int i = 0;
        if (items != null) {
            if (items.size() > 0) {
                for (AuctionItem item : items) {
                    i += item.getPrice();
                }
            }
        }
        return i;
    }

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    private Set<AuctionItem> items;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    private Set<Bid> bids;
}
