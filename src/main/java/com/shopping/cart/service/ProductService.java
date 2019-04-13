package com.shopping.cart.service;

import java.util.Iterator;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.shopping.cart.exception.ResourceNotFoundException;
import com.shopping.cart.model.Product;
import com.shopping.cart.repository.ProductRepository;

@Service
@Transactional
public class ProductService {
	ProductRepository productRepository;

	ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Iterable<Product> getProductList() {
		return productRepository.findAll();
	}

	public Product getProduct(long id) {
		// TODO Auto-generated method stub
		return productRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
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

}
