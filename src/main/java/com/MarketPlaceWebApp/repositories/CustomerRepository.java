package com.MarketPlaceWebApp.repositories;

import com.MarketPlaceWebApp.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    //CRUD C: Create, Read, Update, Delete
    public Optional<Customer> findCustomerByEmail(String email);
}
