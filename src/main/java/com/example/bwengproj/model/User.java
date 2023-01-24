package com.example.bwengproj.model;

import com.example.bwengproj.model.status.Role;
import com.example.bwengproj.model.status.UserStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user")
@Getter
@Setter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Auction> auctions = new HashSet<>();

    public void addAuction(Auction auction) {
        auctions.add(auction);
        auction.setUser(this);
    }

    public void removeAuction(Auction auction) {
        auctions.remove(auction);
        auction.setUser(null);
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bid> bids = new HashSet<>();

    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setUser(this);
    }

    public void removeBid(Bid bid) {
        bids.remove(bid);
        bid.setUser(null);
    }

    @NotBlank
    @Column(name = "first_name")
    private String firstname;

    @NotBlank
    @Column(name = "last_name")
    private String lastname;

    @NotBlank
    @Email
    @Column(name = "email")
    private String email;

    @NotBlank
    @Column(name = "user_name")
    private String username;

    @NotBlank
    @Column(name = "password")
    private String password;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;

    @NotBlank
    @Column(name = "imagePath")
    private String imagePath;
}