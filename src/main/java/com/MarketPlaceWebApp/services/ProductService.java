package com.MarketPlaceWebApp.services;

import com.MarketPlaceWebApp.exceptions.AllExceptionsClass;
import com.MarketPlaceWebApp.models.Product;
import com.MarketPlaceWebApp.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(String productName, String desc, String cat){
        Product product = new Product(productName, desc, cat);
        try{
            log.info("Attempting to save product...");
            productRepository.save(product);
        }catch(DataAccessException e) {
            log.warn("The database is inaccessible at the moment...");
            throw e;
        }
    }
    public Product findProduct(UUID serial){
        return productRepository.findProductBySerialNumber(serial).orElseThrow(()->
                new AllExceptionsClass.UnableToCreateProduct("Failed to create product..."));
    }

    public void deleteProduct(UUID serial){
        productRepository.delete(findProduct(serial));
    }

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }
}
