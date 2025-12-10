package com.uc14.atv1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan; // Importe este

@SpringBootApplication
@ComponentScan(basePackages = "com.uc14.atv1")
public class Atv1Application {
    public static void main(String[] args) {
        SpringApplication.run(Atv1Application.class, args);
    }
}
