package com.shopping.cart.dto;

import com.shopping.cart.model.Product;

public class OrderProductDto {

	Product product;
	Integer quantity;

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

}
