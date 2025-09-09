package com.MarketPlaceWebApp.services;

import com.MarketPlaceWebApp.enums.Role;
import com.MarketPlaceWebApp.exceptions.AllExceptionsClass;
import com.MarketPlaceWebApp.models.Auth;
import com.MarketPlaceWebApp.repositories.AuthRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;

    public void createAuth(String email, String password){
        String hashPassword = passwordEncoder.encode(password);

        try{
            authRepository.save(new Auth(email, password, Role.ROLE_USER.getRole()));
        } catch(DataAccessException e){
            log.warn("unable to save authentication credentials");
            throw new AllExceptionsClass.UnableToCreateAuthException("Unable to access database...");
        }
    }
    //HR-ADMIN Privileges Required
    //--This shall not be included at runtime for now. Do not plan to dynamically assign roles
    public void createAuth(String email, String password, String role) {
    //typed in roles?
        String hashPassword = passwordEncoder.encode(password);

        List<String> possibleRoles = Arrays.stream(Role.values())
                .map(Role::getRole).toList();

        if(!possibleRoles.contains(role.toLowerCase())){
            throw new AllExceptionsClass.CreateNewRoleException("Role does not exist. Create new role first");
            //--This shall not be included at runtime for now.
            //-- Do not plan to dynamically assign roles
        }

        try{
            authRepository.save(new Auth(email, password, role.toLowerCase()));
        } catch(DataAccessException e){
            log.warn("unable to save authentication credentials");
            throw new AllExceptionsClass.UnableToCreateAuthException
                    ("Unable to create authentication credentials...");
        }
    }

    public Auth findByEmail(String email){
        return authRepository.findByEmail(email).orElseThrow(
                ()-> new AllExceptionsClass.UnableToFindAuthCredentials("Email does not exist")
        );
    }
}
