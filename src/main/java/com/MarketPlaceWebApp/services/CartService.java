package com.MarketPlaceWebApp.services;

import com.MarketPlaceWebApp.enums.CartAction;
import com.MarketPlaceWebApp.exceptions.AllExceptionsClass;
import com.MarketPlaceWebApp.models.Cart;
import com.MarketPlaceWebApp.models.Customer;
import com.MarketPlaceWebApp.models.Product;
import com.MarketPlaceWebApp.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository cartRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    @Transactional
    public Cart createCart(Customer customer){
        Cart cart = new Cart();
        cart.setCustomer(customer);
        try{
            log.info("Attempting to create cart...");
            cartRepository.save(cart);
        }catch(DataAccessException e){
            log.warn("Database not available at the moment...");
            throw e;
        }
        return cart;
    }

    @Transactional
    public void deleteCart(String email){
        Customer customerToDelete = customerService.findCustomerByEmail(email);
        try{
            log.info("Deleting cart...");
            cartRepository.delete(customerToDelete.getCart());
        } catch(DataAccessException e){
            log.warn("Database unavailable at the moment...");
            throw e;
        }

    }

    public Cart getCustomerCart(String email){
        Customer customer = customerService.findCustomerByEmail(email);
        Cart cart = customer.getCart();
        if(cart == null){
            throw new AllExceptionsClass.CustomerHasNoCartException(
                    "Customer has no cart assigned...");
        }
        return cart;
    }

    public Map<Product, Integer> getCartProducts(String email){
        Cart cart = getCustomerCart(email);
        return cart.getProduct();
    }

    @Transactional
    public void updateCart(String email, UUID productSerial, CartAction action, int quantity){
        Customer customer = customerService.findCustomerByEmail(email);
        Product product = productService.findProduct(productSerial);
        //If customer exists, it would definitely have a cart...
        Cart cart = customer.getCart();

        switch(action){
            case ADD -> {
                log.info("Adding {} to {}'s cart...", productSerial, customer.getEmail());
                cart.getProduct().put(product, quantity);
                break;
            }
            case REMOVE -> {
                log.info("Removing {} from {}'s cart...", productSerial, customer.getEmail());
                cart.getProduct().remove(product);
                break;
            }
            default -> {
                log.warn("Attempting to do action on cart...");
                throw new AllExceptionsClass.InvalidCartActionException("Argument not acceptable here...");
            }
        }
        cartRepository.save(cart);
    }

    @Transactional
    public void updateCart(Cart cart){
        cartRepository.save(cart);
    }
}
