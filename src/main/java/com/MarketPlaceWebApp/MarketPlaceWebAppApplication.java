package com.MarketPlaceWebApp;

import com.MarketPlaceWebApp.ComponentBean.BeanA;
import com.MarketPlaceWebApp.ComponentBean.ComponentBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages = "com.MarketPlaceWebApp.ComponentBean")
//This is the packages that hold the beans as configured
public class MarketPlaceWebAppApplication {

	public static void main(String[] args) {

		ApplicationContext iocContainer =SpringApplication.run(MarketPlaceWebAppApplication.class, args);

		ComponentBean bean = (ComponentBean) iocContainer.getBean("ComponentBean");
		bean.whoAmI();

		BeanA beanA = (BeanA) iocContainer.getBean("beanABean");

		beanA.printOut();
	}

}
