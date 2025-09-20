package com.MarketPlaceWebApp.models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@RequiredArgsConstructor
@Setter
@Getter
public class Cart {
    private int cartId;
    private UUID cardSerial;

    @OneToOne(mappedBy = "customer_id")
    Customer customer;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Map<Product, Integer> product;
}
