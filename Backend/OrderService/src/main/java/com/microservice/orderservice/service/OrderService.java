package com.microservice.orderservice.service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import com.microservice.orderservice.dto.InventoryResponse;
import com.microservice.orderservice.dto.OrderLineItemsDto;
import com.microservice.orderservice.dto.OrderPlacedEvent;
import com.microservice.orderservice.dto.OrderRequest;
import com.microservice.orderservice.model.Order;
import com.microservice.orderservice.model.OrderLineItems;
import com.microservice.orderservice.repository.OrderRepository;

@Service
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private WebClient.Builder webClient;
	
	@Autowired
	private Tracer trace;

	@Autowired
	private KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;
	
	public String placeOrder(OrderRequest orderRequest) {
		Order order = new Order();
		order.setOrderNumber(UUID.randomUUID().toString());
		
		List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItems().stream().map(orderLineItemsDto->mapToDto(orderLineItemsDto)).toList();
		order.setOrderLineItems(orderLineItems);
		
		List<String> skuCodes = order.getOrderLineItems().stream().map(orderLineItem->orderLineItem.getSkuCode())
		.toList();
		
		Span inventoryServiceLookup= trace.nextSpan().name("InventoryServiceLookup");
		
		try(Tracer.SpanInScope spanInScope = trace.withSpan(inventoryServiceLookup.start())){
			InventoryResponse[] inventoryResponses  =  webClient.build().get().uri("http://InventoryService/api/inventory",
					UriBuilder->UriBuilder.queryParam("skuCode", skuCodes).build())
			.retrieve()
			.bodyToMono(InventoryResponse[].class)
			.block();
			
			boolean allProductsInStock =  Arrays.stream(inventoryResponses).allMatch(inventoryResponse->inventoryResponse.isInStock());
			if(allProductsInStock) {
				orderRepository.save(order);
				kafkaTemplate.send("order-events-notification-topic", new OrderPlacedEvent(order.getOrderNumber(), orderRequest.getEmail()));
				return "Order Placed Successfully";
			}else {
				throw new IllegalArgumentException("Product not in stock, please try again later");
			}
		}finally {
			inventoryServiceLookup.end();
		}
		
		
	}
	
	public OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
		OrderLineItems orderLineItems = new OrderLineItems();
		orderLineItems.setPrice(orderLineItemsDto.getPrice());
		orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
		orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
		return orderLineItems;
	}
}
