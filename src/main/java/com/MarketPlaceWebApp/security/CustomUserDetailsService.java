package com.MarketPlaceWebApp.security;

import com.MarketPlaceWebApp.models.Auth;
import com.MarketPlaceWebApp.services.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final AuthService authService;
    @Override
    public UserDetails loadUserByUsername(String email){
        Auth auth = authService.findByEmail(email);

        return User.withUsername(auth.getEmail())
                .password(auth.getPassword())
                .roles(auth.getRole())
                .build();
    }
}
