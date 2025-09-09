package com.MarketPlaceWebApp.enums;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_ADMIN("admin"),
    ROLE_USER("user");

    private final String role;

    private Role(String role){
        this.role = role;
    }
}
