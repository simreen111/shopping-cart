package com.shopping.cart;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.shopping.cart.model.Product;
import com.shopping.cart.service.ProductService;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	

	@Bean
    CommandLineRunner runner(ProductService productService) {
        return args -> {
            productService.addProduct(new Product(1L, "Apple", 10));
            productService.addProduct(new Product(2L, "Orange", 5));
        };
    }

}
