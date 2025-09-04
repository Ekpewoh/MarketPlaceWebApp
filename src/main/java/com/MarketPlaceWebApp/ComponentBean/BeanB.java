package com.MarketPlaceWebApp.ComponentBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BeanB {

    private BeanC beanC;

    @Autowired
    public BeanB(BeanC c){
        this.beanC = c;
    }
    public void printOut(){
        this.beanC.printOut();
    }
}
