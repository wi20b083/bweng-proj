package com.example.bwengproj.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String imgLink;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<AuctionItem> products;
}
