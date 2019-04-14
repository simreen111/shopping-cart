package com.shopping.cart.service;

import java.util.Iterator;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shopping.cart.exception.OrderQuantityExceededException;
import com.shopping.cart.exception.ResourceNotFoundException;
import com.shopping.cart.model.Product;
import com.shopping.cart.model.Quantity;
import com.shopping.cart.repository.ProductRepository;

@Service
@Transactional
public class ProductService {
	ProductRepository productRepository;

	ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Iterable<Product> getProductList() {
		Iterable <Product> list = productRepository.findAll();
		Iterator<Product> iter = list.iterator();
		while (iter.hasNext()) {
			if(iter.next().getProductCount() <= 0) {
				iter.remove();
			}
		}
		return list;
	}

	public Product getProduct(long id) {
		// TODO Auto-generated method stub
		Product product = productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
		if(product.getProductCount() <= 0) {
			productRepository.delete(product);
			throw new ResourceNotFoundException("Product out of stock");
		}
		return product;
	}

	public void addProduct(Product newProduct) {
		// TODO Auto-generated method stub
		boolean exists = false;
		if(newProduct != null) {
			Iterable<Product> list = getProductList();
			Iterator<Product> iter = list.iterator();
			while (iter.hasNext()) {
				if(newProduct.getProductID() == iter.next().getProductID()) {
					exists = true;
				}
			}

			if (exists) {
				Product currentProduct = getProduct(newProduct.getProductID());
				newProduct.setProductCount(newProduct.getProductCount() + currentProduct.getProductCount());
			}
			productRepository.save(newProduct);
		} else {
			new NullPointerException("Null product passed. Please add a valid product");
		}

	}

	public void deleteProduct(long id) {
		// TODO Auto-generated method stub
		Product product = getProduct(id);

		if (product.getProductCount() > 1) {
			product.setProductCount(product.getProductCount() - 1);
			productRepository.save(product);
		} else if (product.getProductCount() == 1) {
			productRepository.delete(product);
		} else {
			new ResourceNotFoundException("No Product found to delete");
		}
	}

	public void checkoutProduct(long id, Quantity quantity) {
		// TODO Auto-generated method stub
		Product product = getProduct(id);
		
		if(quantity.getQuantity() <= product.getProductCount()) {
			product.setProductCount(product.getProductCount() - quantity.getQuantity());
			productRepository.save(product);
		}
		else {
			new OrderQuantityExceededException("Specified quantity of " + product.getProductName() + " is not available in inventory. Currently, only " + product.getProductCount() + " " + product.getProductName() + " are available.");
		}
	}

}
