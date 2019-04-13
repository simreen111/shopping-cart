package com.shopping.cart.repository;

import org.springframework.data.repository.CrudRepository;

import com.shopping.cart.model.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
