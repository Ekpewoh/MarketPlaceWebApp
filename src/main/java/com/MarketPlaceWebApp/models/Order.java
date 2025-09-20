package com.MarketPlaceWebApp.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    @Column(name="order_serial_id", unique = true, nullable = false)
    private UUID orderSerial;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();

    public Order (Customer customer, List<OrderItem> orderItemList){
        this.customer=customer;
        this.orderItemList=orderItemList;
    }
    @PrePersist
    public void generateSerialId(){
        if(orderSerial==null){
            orderSerial=UUID.randomUUID();
        }
    }
}
