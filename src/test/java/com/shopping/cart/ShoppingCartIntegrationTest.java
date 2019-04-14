package com.shopping.cart;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.hamcrest.Matchers.*;

import org.assertj.core.api.Assertions;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.shopping.cart.controller.ShoppingCartController;
import com.shopping.cart.model.Product;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { DemoApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShoppingCartIntegrationTest {

	@Autowired
	private ShoppingCartController shoppingCartController;

	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	private int port;

	@Test
	public void contextLoads() {
		Assertions.assertThat(shoppingCartController).isNotNull();
	}

	@Test
	public void test_A_GetProducts() {
		ResponseEntity<Iterable<Product>> responseEntity = restTemplate.exchange(
				"http://localhost:" + port + "/products", HttpMethod.GET, null,
				new ParameterizedTypeReference<Iterable<Product>>() {
				});

		Iterable<Product> products = responseEntity.getBody();
		assertThat(products, hasItem(hasProperty("productName", is("Apple"))));
		assertThat(products, hasItem(hasProperty("productName", is("Orange"))));
		assertThat(products, hasItem(hasProperty("productName", is("Cherry"))));
		assertThat(products, hasItem(hasProperty("productName", is("Mango"))));
	}

	@Test
	public void test_B_GetProductsById() {
		ResponseEntity<Product> response = restTemplate.exchange("http://localhost:" + port + "/products/1",
				HttpMethod.GET, null, new ParameterizedTypeReference<Product>() {
				});

		Product product = response.getBody();
		MatcherAssert.assertThat(product, hasProperty("productName", is("Apple")));
	}

	@Test
	public void test_C_AddProduct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestJson = "{\r\n" + "    \"productName\": \"Pineapple\",\r\n" + "    \"productID\": 5,\r\n"
				+ "    \"productCount\": 8\r\n" + "}";
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/products",
				HttpMethod.PUT, entity, new ParameterizedTypeReference<String>() {
				});
		String responseString = responseEntity.getBody().toString();
		assertEquals(responseString, "Product added successfully");
	}

	@Test
	public void test_D_DeleteProductsById() {
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/products/2",
				HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
				});

		String responseString = response.getBody().toString();
		assertEquals(responseString, "Product deleted successfully");
	}

	@Test
	public void test_E_DeleteProductsById() {
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/products/123",
				HttpMethod.DELETE, null, new ParameterizedTypeReference<String>() {
				});

		String responseString = response.getBody().toString();
		assertEquals(responseString, "Product doesn't exist");
	}

	@Test
	public void test_F_CheckoutProduct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestJson = "{\r\n" + "    \"quantity\": 2\r\n" + "}";
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/checkout/3",
				HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
				});
		String responseString = responseEntity.getBody().toString();
		assertEquals(responseString, "Product ordered successfully");
	}

	@Test
	public void test_G_CheckoutProduct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestJson = "{\r\n" + "    \"quantity\": 600\r\n" + "}";
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/checkout/40",
				HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
				});
		String responseString = responseEntity.getBody().toString();
		assertEquals(responseString, "Ordered product quantity exceeds current product inventory count");
	}
	
	@Test
	public void test_H_CheckoutProduct() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestJson = "{\r\n" + "    \"quantity\": 6000\r\n" + "}";
		HttpEntity<String> entity = new HttpEntity<String>(requestJson, headers);
		ResponseEntity<String> responseEntity = restTemplate.exchange("http://localhost:" + port + "/checkout/4",
				HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
				});
		String responseString = responseEntity.getBody().toString();
		assertEquals(responseString, "Ordered product quantity exceeds current product inventory count");
	}

}
