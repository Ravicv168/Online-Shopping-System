package com.microservice.inventoryservice.service;

import java.util.List;
import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice.inventoryservice.dto.InventoryResponse;
import com.microservice.inventoryservice.repository.InventoryRepository;

@Service
public class InventoryService {
	
	@Autowired
	private InventoryRepository inventoryRepository;
	
	private Logger log = LoggerFactory.getLogger(InventoryService.class);
	
	@Transactional(readOnly = true)
	public List<InventoryResponse> isInStock(List<String> skuCode) {
		
		log.info("Wait Started..");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info("Wait Ended..");
		return inventoryRepository.findBySkuCodeIn(skuCode)
				.stream()
				.map(inventory-> new InventoryResponse(inventory.getSkuCode(), inventory.getQuantity() > 0))
				.toList();
				
	}
}
