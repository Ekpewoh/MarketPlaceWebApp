package com.MarketPlaceWebApp.services;

import com.MarketPlaceWebApp.enums.CartAction;
import com.MarketPlaceWebApp.exceptions.AllExceptionsClass;
import com.MarketPlaceWebApp.models.*;
import com.MarketPlaceWebApp.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.id.uuid.UuidValueGenerator;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final CustomerService customerService;
    private final CartService cartService;
    private final OrderItemService orderItemService;

    @Transactional
    public void purchaseItem(Product product, String email, int quantity){
        try{
            Product productPurchased = productService.findProduct(product.getSerialNumber());
            Customer customerPurchasing = customerService.findCustomerByEmail(email);

            if(quantity > productPurchased.getQty()){
                throw new AllExceptionsClass.InsufficientQuantityOfProduct
                        ("insufficient quantity of product in store...");
            }
            OrderItem orderItem = new OrderItem();
            orderItem.setProductName(productPurchased.getProductName());
            orderItem.setQtyPurchased(quantity);
            orderItem.setProductPrice(productPurchased.getPrice());
            orderItem.setSubtotal(quantity*productPurchased.getPrice());
            orderItem.setProduct(productPurchased);

            List<OrderItem> orderItemList = new ArrayList<>();
            orderItemList.add(orderItem);

            Order order = new Order();
            order.setOrderItemList(orderItemList);
            order.setCustomer(customerPurchasing);

            orderItem.setOrder(order);

            productPurchased.setQty(productPurchased.getQty() - quantity);

            productService.updateProduct(productPurchased);
            orderItemService.updateOrderItem(orderItem);
            //Let's make this a batch store
            orderRepository.save(order);

        }catch(DataAccessException e){
            log.warn("Certain database not available...{}", e.getMessage());
        }
    }

    @Transactional
    public void purchaseItemsInCart(String email){
        Customer customer = customerService.findCustomerByEmail(email);
        Map<Product, Integer> productMap = customer.getCart().getProduct();

        List<Product> purchase = new ArrayList<>(productMap.keySet());
        for(Product p : purchase){
            purchaseItem(p, email, productMap.get(p));
            cartService.updateCart(email,p.getSerialNumber(),CartAction.REMOVE, productMap.get(p));
        }
    }

    @Transactional
    public void purchaseItemsInCartFasterVersion(String email){
        //No logs for each
        //Use for bulk purchases above 10.

        try{
            Customer customer = customerService.findCustomerByEmail(email);
            Map<Product, Integer> productMap = customer.getCart().getProduct();
            List<Product> purchase = new ArrayList<>(productMap.keySet());

            List<OrderItem> orderItemList = new ArrayList<>();
            for(Product p : purchase){
                int quantity = productMap.get(p);
                if( quantity > p.getQty()){
                    throw new AllExceptionsClass.InsufficientQuantityOfProduct
                            ("insufficient quantity of product in store...");
                }
                orderItemList.add(buildOrderItem(p, quantity));
                p.setQty(p.getQty() - quantity);
            }

            Order order = new Order();
            order.setCustomer(customer);
            order.setOrderItemList(orderItemList);
            orderItemList.forEach(oi->oi.setOrder(order));

            productMap.clear();
            orderRepository.save(order);


        }catch (DataAccessException | AllExceptionsClass.InsufficientQuantityOfProduct e){
            log.warn("error occurred...{}", e.getMessage());
        }
    }

    public OrderItem buildOrderItem(Product productPurchased, int quantity){

        OrderItem orderItem = new OrderItem();
        orderItem.setProductName(productPurchased.getProductName());
        orderItem.setQtyPurchased(quantity);
        orderItem.setProductPrice(productPurchased.getPrice());
        orderItem.setSubtotal(quantity*productPurchased.getPrice());
        orderItem.setProduct(productPurchased);

        return orderItem;
    }

    //Handling Returns
    public void returnItem(String orderSerial, Product product, int quantity){
        try{
            Order order = orderRepository.findByOrderSerial(UUID.fromString(orderSerial));
            if(order==null){
                throw new AllExceptionsClass.OrderNotFromStoreException
                        ("illegal receipt: order is not from store...");
            }

            OrderItem orderItem = order.getOrderItemList()
                    .stream()
                    .filter(oi -> oi.getProduct().equals(product))
                    .findFirst()
                    .orElseThrow(()-> new AllExceptionsClass.OrderDoesNotContainItemException
                            ("illegal receipt: item not on order..."));
            if(orderItem.getQtyPurchased() < quantity){
                throw new AllExceptionsClass.ItemsOnReceiptMoreThanPurchasedException
                        ("error: customer can not return more than previously purchased...");
            }
            product.setQty(product.getQty()+quantity);
            productService.updateProduct(product);

            orderItem.setQtyPurchased(orderItem.getQtyPurchased()-quantity);
            orderItem.setSubtotal(orderItem.getProductPrice()*orderItem.getQtyPurchased());

            if(orderItem.getQtyPurchased() ==  0){
                order.getOrderItemList().remove(orderItem);
                orderItemService.deleteOrderItem(orderItem);
            }

            orderRepository.save(order);

        }catch(DataAccessException | AllExceptionsClass.OrderNotFromStoreException |
                AllExceptionsClass.OrderDoesNotContainItemException |
                AllExceptionsClass.ItemsOnReceiptMoreThanPurchasedException e){
            log.warn("error encountered: {}", e.getMessage());
        }
    }
}
