package com.MarketPlaceWebApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    //Private facing ID
    private Long customerId;

    //Public facing ID
    @Column(name="serialId")
    private UUID customerUuid = UUID.randomUUID();

    //Customer Identifiable Data
    @Column(name="first_name", nullable = false)
    private String fName;
    @Column(name="last_name")
    private String lName;
    @Column(name="email", unique = true, nullable = false)
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="auth_id", referencedColumnName = "customer_id")
    private Auth auth;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> product;

    @OneToOne
    @JoinColumn(name="cart_id", referencedColumnName = "customer_id")
    private Cart cart;

    public Customer(String fName, String lName, String email){
        this.fName = fName;
        this.lName = lName;
        this.email = email;
    }

}
