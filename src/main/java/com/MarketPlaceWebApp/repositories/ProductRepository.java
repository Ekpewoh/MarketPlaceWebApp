package com.MarketPlaceWebApp.repositories;

import com.MarketPlaceWebApp.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    //CRUD C: Create, Read, Update, Delete Available
    public Optional<Product> findProductBySerialNumber(UUID serialNumber);
}
