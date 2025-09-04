package com.MarketPlaceWebApp.ComponentBean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BeanC {

    //We can create a default valued string that is used when the bean is created
    @Value("This is the default value")
    String str;
    public void printOut(){
        System.out.println(str);
    }
}
