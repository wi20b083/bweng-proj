package com.example.bwengproj.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    private String firstName;
    private String lastName;
    private String imgLink;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Auction> auctions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Bid> bids;

}
