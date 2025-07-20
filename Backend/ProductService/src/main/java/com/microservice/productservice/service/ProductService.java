package com.microservice.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.microservice.productservice.dto.ProductRequest;
import com.microservice.productservice.dto.ProductResponse;
import com.microservice.productservice.model.Product;
import com.microservice.productservice.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	private ProductRepository productRepository;

	public ProductResponse createProduct(ProductRequest prodRequest) {
		Product product = new Product();
		product.setName(prodRequest.getName());
		product.setDescription(prodRequest.getDescription());
		product.setPrice(prodRequest.getPrice());
		
		productRepository.save(product);
		return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
	}
	
	public List<ProductResponse> getAllProducts() {
		return productRepository.findAll().stream()
				.map(product->new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice()))
				.toList();
	}
}
