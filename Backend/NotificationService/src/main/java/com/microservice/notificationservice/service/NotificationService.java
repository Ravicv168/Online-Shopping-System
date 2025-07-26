package com.microservice.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
	
	private Logger log = LoggerFactory.getLogger(NotificationService.class);

	@KafkaListener(topics = "order-events-notification-topic")
	public void consume(String message) {
		log.info("Received message: " + message);
	}
}
