package com.MarketPlaceWebApp.services;

import com.MarketPlaceWebApp.models.OrderItem;
import com.MarketPlaceWebApp.repositories.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;

    public OrderItem createOrderItem(OrderItem orderItem){
        return orderItemRepository.save(orderItem);
    }

    public void updateOrderItem(OrderItem orderItem){
        orderItemRepository.save(orderItem);
    }
    public void deleteOrderItem(OrderItem orderItem){
        orderItemRepository.delete(orderItem);
    }
}
