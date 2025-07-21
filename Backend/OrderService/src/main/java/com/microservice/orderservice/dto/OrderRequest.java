package com.microservice.orderservice.dto;
import java.util.*;

public class OrderRequest {

	private List<OrderLineItemsDto> orderLineItems;
	
	public OrderRequest() {
		// TODO Auto-generated constructor stub
	}
	public OrderRequest(List<OrderLineItemsDto> orderLineItems) {
		super();
		this.orderLineItems = orderLineItems;
	}

	public List<OrderLineItemsDto> getOrderLineItems() {
		return orderLineItems;
	}

	public void setOrderLineItems(List<OrderLineItemsDto> orderLineItems) {
		this.orderLineItems = orderLineItems;
	}
	
	
}
