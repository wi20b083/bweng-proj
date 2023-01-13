package com.example.bwengproj.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Role> roles = new ArrayList<>();

    @NotNull
    @NotBlank
    @NotEmpty
    private String firstName;

    @NotNull
    @NotBlank
    @NotEmpty
    private String lastName;

    @NotNull
    @NotBlank
    @NotEmpty
    private String imgLink;

    @Column(unique = true)
    @NotNull
    @NotBlank
    @NotEmpty
    private String userName;

    @NotNull
    @NotBlank
    @NotEmpty
    private String password;

    @Column(unique = true)
    @NotNull
    @NotBlank
    @NotEmpty
    @Email
    private String email;

    private boolean status;

    @NotNull
    @NotBlank
    @NotEmpty
    private String address;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Auction> auctions;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Bid> bids;

}
