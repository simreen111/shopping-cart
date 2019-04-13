package com.shopping.cart.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
	
	String productName;
	@Id
	long productID;
	long productCount;
	public Product(long id, String name, int count) {
		// TODO Auto-generated constructor stub
		this.productID = id;
		this.productName = name;
		this.productCount = count;
	}
	
	public Product() {
		
	}
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public long getProductID() {
		return productID;
	}
	public void setProductID(long productID) {
		this.productID = productID;
	}
	public long getProductCount() {
		return productCount;
	}
	public void setProductCount(long productCount) {
		this.productCount = productCount;
	}
	@Override
	public String toString() {
		return "Product [productName=" + productName + ", productID=" + productID + ", productCount=" + productCount
				+ "]";
	}
	
}
