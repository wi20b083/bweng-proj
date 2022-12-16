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
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull(message = "Start date is required.")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date startDate;

    @NotNull(message = "End date is required.")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date endDate;


    private boolean status;

    @NotNull(message = "Delivery date is required.")
    @DateTimeFormat(pattern = "dd.MM.yyyy HH:mm")
    private Date deliveryDate;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    private Set<AuctionItem> items;
    private int total = total();
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auction")
    private Set<Bid> bids;

    private int total() {
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
}
