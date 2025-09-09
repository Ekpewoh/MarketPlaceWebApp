package com.MarketPlaceWebApp.repositories;

import com.MarketPlaceWebApp.models.Auth;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRepository extends JpaRepository<Auth, Integer> {
    //CRUD C: Create, Read, Update, Delete
    Optional<Auth> findByEmail(String email);
}
