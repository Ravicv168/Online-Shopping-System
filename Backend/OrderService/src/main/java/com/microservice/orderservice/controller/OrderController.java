package com.microservice.orderservice.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.orderservice.dto.OrderRequest;
import com.microservice.orderservice.service.OrderService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import io.netty.util.concurrent.CompleteFuture;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	@Autowired
	private OrderService orderService;

	@PostMapping
	@CircuitBreaker(name = "inventory", fallbackMethod = "fallBackMethod")
	@TimeLimiter(name = "inventory")
	@Retry(name = "inventory")
	public CompletableFuture<String> placeOrder(@RequestBody OrderRequest orderRequest) {
		return CompletableFuture.supplyAsync(()->orderService.placeOrder(orderRequest));
	}
	
	public CompletableFuture<String> fallBackMethod(OrderRequest orderRequest, RuntimeException runtimeException) {
		return CompletableFuture.supplyAsync(()->"Inventory service is currently unavailable. Please try again later.");
	}
}
