package com.MarketPlaceWebApp.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemId;

    @Column(name="product_name")
    private String productName;

    @Column(name="product_price")
    private double productPrice;

    @Column(name = "qty_purchased")
    private int qtyPurchased;

    @Column(name = "subtotal")
    private double subtotal;

    @ManyToOne
    @JoinColumn(name="order_id")
    Order order;

    @ManyToOne
    @JoinColumn(name="product_id")
    Product product;

    public OrderItem(String productName, double productPrice, int qtyPurchased, double subtotal){
        this.productName=productName;
        this.productPrice=productPrice;
        this.qtyPurchased=qtyPurchased;
        this.subtotal=subtotal;
    }

}
