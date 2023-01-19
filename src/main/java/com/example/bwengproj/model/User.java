package com.example.bwengproj.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
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
import java.util.HashSet;
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

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "USER_ROLES", joinColumns = @JoinColumn(name = "USER_ID"))
    @Column(name = "ROLE", nullable = false)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>(List.of(Role.ROLE_USER));

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
    @JsonIgnore
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
    private Set<Auction> auctions = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private Set<Bid> bids = new HashSet<>();


    //for update user without password
    public User(Long id, String firstName, String lastName, String imgLink, String userName, String email, String address, boolean status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.imgLink = imgLink;
        this.userName = userName;
        this.email = email;
        this.address = address;
        this.status = status;
        this.password = null;
    }

}
