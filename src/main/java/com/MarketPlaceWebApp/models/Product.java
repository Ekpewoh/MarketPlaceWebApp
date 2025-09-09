package com.MarketPlaceWebApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.annotation.processing.Generated;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter

@Table(
        name = "product_table",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"serialNumber"})
        }
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;
    @Column(name="serial_number")
    private UUID serialNumber;

    @Column
    private String productName;

    @Column(name="description")
    private String desc;

    @Column(name="category")
    private String cat;

    @Column(nullable = false)
    private int qty;

    @Column(nullable = false)
    private double price;

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    public Product(String productName, String desc, String cat){
        this.desc = desc;
        this.cat = cat;
        this.productName = productName;
    }

    @PrePersist
    public void setSerialNumber(){
        if(serialNumber==null){
            serialNumber=UUID.randomUUID();
        }
    }
}
