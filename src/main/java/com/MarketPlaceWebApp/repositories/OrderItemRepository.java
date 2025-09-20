package com.MarketPlaceWebApp.repositories;

import com.MarketPlaceWebApp.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {
}
