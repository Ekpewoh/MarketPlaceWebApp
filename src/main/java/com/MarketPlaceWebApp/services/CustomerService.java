package com.MarketPlaceWebApp.services;
import com.MarketPlaceWebApp.exceptions.AllExceptionsClass;
import com.MarketPlaceWebApp.models.Customer;
import com.MarketPlaceWebApp.repositories.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthService authService;
    @Autowired
    public CustomerService(CustomerRepository customerRepository,
                           AuthService authService){
        this.customerRepository = customerRepository;
        this.authService = authService;
    }
    public void createCustomer(String fname, String lname, String email, String password) {
        Customer customer = new Customer(
                fname, lname, email
        );
        try{
            log.info("Attempting to log customer...");
            customerRepository.save(customer);
            authService.createAuth(email, password);
        } catch(DataAccessException | AllExceptionsClass.UnableToCreateAuthException e){
            //Catching lower level exceptions
            log.warn("User was not created...{}", e.getMessage());
            throw new AllExceptionsClass.UnableToCreateCustomerException("Failed to create customer...");
        }
    }
    public void createUser(String fname, String lname, String email, String password, String role){
        Customer customer = new Customer(
                fname, lname, email
        );
        try{
            log.info("Attempting to log customer...");
            customerRepository.save(customer);
            authService.createAuth(email, password, role);
            //HR-ADMIN assigned roles are available. Roles must be set by design engineers
        } catch(DataAccessException e) {
            log.warn("The database is inaccessible at the moment...");
            throw new AllExceptionsClass.UnableToCreateCustomerException("Unable to create user...");
        } catch (AllExceptionsClass.UnableToCreateAuthException
                | AllExceptionsClass.CreateNewRoleException e){
            log.warn("Unable to create user...");
            throw e;
        }
    }
    public Customer findCustomerByEmail(String email) {
        return customerRepository.findCustomerByEmail(email)
                .orElseThrow(() -> new AllExceptionsClass.CustomerNotFoundException("Check credentials and try again..."));
    }
    public void deleteCustomer(String email){
            customerRepository.delete(findCustomerByEmail(email));
    }

    public List<Customer> getAllCustomers(){
        List<Customer> customers = new ArrayList<>();
        return customerRepository.findAll();
    }

    public void updateCustomer(Map<String, String> mapUpdates, String email) {
        Customer customer = findCustomerByEmail(email);
        mapUpdates.forEach((key, value)->{
            switch(key){
                case "fname" -> {if(value!=null) customer.setFName(value);}
                case "lname" -> {if(value!=null) customer.setLName(value);}
                case "email" -> {if(value!=null) customer.setEmail(value);}
                default ->{}
            }
        });
        customerRepository.save(customer);
    }
}
