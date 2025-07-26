# 🛒 Online Shopping Microservices Application

This is a microservices-based online shopping system built using Spring Boot, Spring Cloud, Kafka, and other cloud-native tools.

## Features
- 📦 Product Management (Add/View Products)
- 🛒 Order Placement (checks inventory before placing)
- 🔁 Async Event Notification using Kafka
- 💥 Circuit Breaker for fallback
- 📉 Distributed Tracing with Zipkin
- ⚙️ Dynamic service registration and routing

## 🔗 Communication Between Services
| From          | To        | Method           | Purpose                  |
| ------------- | --------- | ---------------- | ------------------------ |
| Order Service | Inventory | WebClient (HTTP) | Check stock availability |
| Order Service | Kafka     | Producer         | Publish order event      |
| Notification  | Kafka     | Consumer         | Listen for new orders    |

## ⚙️ Tech Stack
- Java
- Spring Boot
- Spring Cloud
- Spring Data JPA
- Eureka Discovery Server
- Spring Cloud Gateway
- Kafka
- Zipkin (Distributed Tracing)
- MySQL

## Installation
### Clone the repository
    git clone https://github.com/Ravicv168/Online-Shopping-System.git

## How to Run:
- Start Kafka/Zookeeper
- Run Zipkin
- Start Service Discovery
- Run each microservice (Product, Inventory, Order, Notification)
- Start API Gateway
- Access services through Gateway (e.g., localhost:8080/api/product)
- Use Zipkin UI (http://localhost:9411) to trace requests

## 🌐 API Gateway Endpoints
- **Add the products**: `POST /api/product`
- **Get all products**: `GET /api/product`
- **Place order**: `POST /api/order`
- **Check inventory**: `GET /api/inventory`

## 🚀 Future Enhancements
- Add frontend
- Add Prometheus & Grafana for monitoring
- Containerize with Docker Compose
