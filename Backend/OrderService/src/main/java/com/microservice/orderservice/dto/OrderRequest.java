package com.microservice.orderservice.dto;
import java.util.*;

public class OrderRequest {

	private List<OrderLineItemsDto> orderLineItems;
	private String email;
	
	public OrderRequest() {
		// TODO Auto-generated constructor stub
	}
	public OrderRequest(List<OrderLineItemsDto> orderLineItems, String email) {
		super();
		this.orderLineItems = orderLineItems;
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public List<OrderLineItemsDto> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<OrderLineItemsDto> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
	
	
}
