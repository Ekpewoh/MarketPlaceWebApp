package com.MarketPlaceWebApp.ComponentBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("beanABean")
public class BeanA {
    private BeanB beanB;

    @Autowired
    public BeanA(BeanB b){
        this.beanB = b;
    }

    public void printOut(){
        this.beanB.printOut();
    }
}
