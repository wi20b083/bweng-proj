package com.example.bwengproj.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(fluent = true, chain = true)
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private Date startDate;
    private Date endDate;
    private boolean status;
    private Date deliveryDate;
    private int total;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
