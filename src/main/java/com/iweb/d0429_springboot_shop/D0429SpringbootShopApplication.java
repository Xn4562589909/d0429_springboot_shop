package com.iweb.d0429_springboot_shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class D0429SpringbootShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(D0429SpringbootShopApplication.class, args);
    }

}
