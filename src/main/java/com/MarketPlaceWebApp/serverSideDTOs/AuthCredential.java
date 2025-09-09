package com.MarketPlaceWebApp.serverSideDTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class AuthCredential {
    String email;
    String password;
}
