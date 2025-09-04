package com.MarketPlaceWebApp.ComponentBean;

import org.springframework.stereotype.Component;

@Component("ComponentBean")
public class ComponentBean {
    public void whoAmI(){
        System.out.println(this);
    }
}
