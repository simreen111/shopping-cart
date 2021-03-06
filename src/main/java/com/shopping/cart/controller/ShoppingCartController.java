package com.shopping.cart.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shopping.cart.exception.OrderQuantityExceededException;
import com.shopping.cart.model.Product;
import com.shopping.cart.model.Quantity;
import com.shopping.cart.service.ProductService;

@RestController
public class ShoppingCartController {

	private static final Logger logger = LoggerFactory.getLogger(ShoppingCartController.class);
	ProductService productService;

	ShoppingCartController(ProductService productService) {
		this.productService = productService;
	}

	@GetMapping("/products")
	public Iterable<Product> getAllProducts() {
		return productService.getProductList();
	}

	@GetMapping("/products/{id}")
	public Product getProductByID(@PathVariable long id) {
		return productService.getProduct(id);
	}

	@PutMapping("/products")
	public ResponseEntity<String> addProduct(@RequestBody Product newProduct) {
		try {
			productService.addProduct(newProduct);
			return new ResponseEntity("Product added successfully", HttpStatus.CREATED);
		} catch (Exception e) {
			logger.error("Unable to add product to inventory", e);
		}
		return new ResponseEntity("Product couldn't be added", HttpStatus.EXPECTATION_FAILED);
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<String> deleteProduct(@PathVariable long id) {
		try {
			productService.deleteProduct(id);
			return new ResponseEntity("Product deleted successfully", HttpStatus.OK);
		} catch (Exception e) {
			logger.error("Product does not exists to delete");
		}
		return new ResponseEntity("Product doesn't exist", HttpStatus.NOT_FOUND);
	}

	@PostMapping("/checkout/{id}")
	public ResponseEntity<String> checkoutProduct(@PathVariable long id, @RequestBody Quantity quantity) {
		try {
			productService.checkoutProduct(id, quantity);
			return new ResponseEntity("Product ordered successfully", HttpStatus.OK);
		} catch(OrderQuantityExceededException e) {
			return new ResponseEntity("Ordered product quantity exceeds current product inventory count", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("Quantity exceeded");
		}
		return new ResponseEntity("Ordered product quantity exceeds current product inventory count", HttpStatus.BAD_REQUEST);
	}
}
