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
            productService.addProduct(new Product(1L, "Pen", 1));
            productService.addProduct(new Product(2L, "Book", 2));
        };
    }

}
