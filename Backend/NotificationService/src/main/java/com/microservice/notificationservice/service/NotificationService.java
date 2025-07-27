package com.microservice.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.microservice.notificationservice.dto.OrderPlacedEvent;

@Service
public class NotificationService {
	
	private Logger log = LoggerFactory.getLogger(NotificationService.class);
	
	@Autowired
	private JavaMailSender mailSender;

	@KafkaListener(topics = "order-events-notification-topic")
	public void consume(OrderPlacedEvent event) {
		log.info("Received message: Order placed successfully, Order id " + event.getOrderNumber());
		String toEmail = event.getEmail();
        String subject = "Order Confirmation";
        String body = "Your order " + event.getOrderNumber() + " has been placed successfully";

		sendEmail(toEmail, subject, body);
		log.info("Email Sent Successfully to " + toEmail);
		
	}
	
	public void sendEmail(String email, String subject, String body) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(email);
		message.setTo(email);
		message.setSubject(subject);
		message.setText(body);
		mailSender.send(message);
	}
}
