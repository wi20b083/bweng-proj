package com.example.bwengproj.model;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Delivery date is required.")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date deliveryDate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bid")
    private Set<BidItem> items;
    private int total = total();
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    private int total() {
        int i = 0;
        if (items != null) {
            if (items.size() > 0) {
                for (BidItem item : items) {
                    i += item.getPrice();
                }
            }
        }
        return i;
    }
}
