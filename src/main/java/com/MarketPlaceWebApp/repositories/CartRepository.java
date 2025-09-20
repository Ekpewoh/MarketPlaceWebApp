package com.MarketPlaceWebApp.repositories;

import com.MarketPlaceWebApp.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    //CRUD functionality
}
