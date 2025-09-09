package com.MarketPlaceWebApp.models;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
public class Auth {
    private Long authId;
    private String email;
    private String password;

    private String role;

    @OneToOne(mappedBy = "customer")
    Customer customer;

    public Auth(String email, String password, String role){
        this.email= email;
        this.password = password;
        this.role = role;
    }

    @PrePersist
    public void setRole(){
        if(role==null){
            role="user";
        }
    }
}


