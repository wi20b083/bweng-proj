package com.example.bwengproj.model;

import com.example.bwengproj.model.status.Role;
import com.example.bwengproj.model.status.UserStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Bid> bids = new HashSet<>();
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
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private UserStatus status;
    @NotBlank
    @Column(name = "image_path")
    private String imagePath;

    public void addAuction(Auction auction) {
        auctions.add(auction);
        auction.setUser(this);
    }

    public void removeAuction(Auction auction) {
        auctions.remove(auction);
        auction.setUser(null);
    }

    public void addBid(Bid bid) {
        bids.add(bid);
        bid.setUser(this);
    }

    public void removeBid(Bid bid) {
        bids.remove(bid);
        bid.setUser(null);
    }

    public void addRole(Role role) {
        roles.add(role);
    }

    public void removeRole(Role role) {
        roles.remove(role);
    }
}