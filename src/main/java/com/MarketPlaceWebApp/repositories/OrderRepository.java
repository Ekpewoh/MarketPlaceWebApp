package com.MarketPlaceWebApp.repositories;

import com.MarketPlaceWebApp.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    public Order findByOrderSerial(UUID orderSerial);
}
