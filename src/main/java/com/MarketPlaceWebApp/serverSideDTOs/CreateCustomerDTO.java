package com.MarketPlaceWebApp.serverSideDTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Setter
@Getter
public class CreateCustomerDTO {
    //String fname, String lname, String email, String password, String role
    String fname;
    String lname;
    String email;
    String password;
    String role;
}
