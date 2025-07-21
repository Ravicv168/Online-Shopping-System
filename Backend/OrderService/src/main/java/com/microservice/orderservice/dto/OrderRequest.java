package com.microservice.orderservice.dto;
import java.util.*;

public class OrderRequest {

	private List<OrderLineItems> orderLineItems;

	public OrderRequest(List<OrderLineItems> orderLineItems) {
		super();
		this.orderLineItems = orderLineItems;
	}

	public List<OrderLineItems> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<OrderLineItems> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
	
	
}
