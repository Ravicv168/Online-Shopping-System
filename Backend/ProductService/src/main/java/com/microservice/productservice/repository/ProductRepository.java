package com.microservice.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.productservice.model.Product;

public interface ProductRepository extends JpaRepository<Product, String>{

}
