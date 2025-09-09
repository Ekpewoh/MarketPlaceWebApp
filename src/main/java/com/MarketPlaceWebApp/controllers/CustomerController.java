package com.MarketPlaceWebApp.controllers;

import com.MarketPlaceWebApp.exceptions.AllExceptionsClass;
import com.MarketPlaceWebApp.models.Customer;
import com.MarketPlaceWebApp.serverSideDTOs.CreateCustomerDTO;
import com.MarketPlaceWebApp.services.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    //This is HR-ADMIN Authorization required
    public ResponseEntity<?> createUser(@RequestBody CreateCustomerDTO customerDTO){
        try{
            customerService.createUser(
                    customerDTO.getFname(),
                    customerDTO.getLname(),
                    customerDTO.getEmail(),
                    customerDTO.getPassword(),
                    customerDTO.getRole()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch(AllExceptionsClass.UnableToCreateCustomerException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
              Map.of("error", e.getMessage(), "status", "500")
            );
        }

    }
    @PostMapping
    //General Customer Creation
    public ResponseEntity<?> createCustomer(@RequestBody CreateCustomerDTO customerDTO){
        try{
            customerService.createCustomer(
                    customerDTO.getFname(),
                    customerDTO.getLname(),
                    customerDTO.getEmail(),
                    customerDTO.getPassword()
            );
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }catch(AllExceptionsClass.UnableToCreateCustomerException e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    Map.of("error", e.getMessage(), "status", "500")
            );
        }
    }

    @GetMapping("/me")
    public ResponseEntity<Customer> getCustomer(@AuthenticationPrincipal UserDetails userDetails){
        String email = userDetails.getUsername();
        return ResponseEntity.ok(customerService.findCustomerByEmail(email));
    }

    @GetMapping
    @PreAuthorize("hasRole('admin)")
    public List<Customer> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @PostMapping("/{email}")
    public ResponseEntity<?> updateCustomer(
            @PathVariable String email,
            @RequestBody Map<String, String> updates,
            @AuthenticationPrincipal UserDetails userDetails
    ){
        String currentEmail = userDetails.getUsername();
        Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();

        boolean isAdmin = roles.stream().anyMatch(a ->a.getAuthority().equals("admin"));
        if(!isAdmin){
            Customer currentCustomer = customerService.findCustomerByEmail(email);
            if(!currentCustomer.getEmail().equals(currentEmail)){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(
                        "You are not allowed to update another customer's details"
                );
            }
        }
        customerService.updateCustomer(updates, email);
        return ResponseEntity.status(HttpStatus.OK).body("Customer updated...");
    }

}
