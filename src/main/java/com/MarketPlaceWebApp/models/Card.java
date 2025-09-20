package com.MarketPlaceWebApp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Entity
@RequiredArgsConstructor
public class Card {
    //This is the card information using PayPal
    private int card_id;

    //PayPal grants bill tokens
    private String billingToken;

    @ManyToOne
    private Customer customer;
}
