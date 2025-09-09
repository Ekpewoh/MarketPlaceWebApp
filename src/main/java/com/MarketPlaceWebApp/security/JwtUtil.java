package com.MarketPlaceWebApp.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String SECRET;
    @Value("${jwt.expiration}")
    private long EXPIRATION;
    //30 minutes

    public String generateToken(String username, String role){
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }

    public String extractUserName(String token){
        return Jwts.parser().setSigningKey(SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validate (String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        } catch (JwtException e){
            return false;
        }
    }
}
