# Microservice Architecture for Order Processing System
This repository is a hobby project designed as a sandbox for exploring microservices architecture through a hands-on approach. 
It's a simulated order processing system that provides a real-world context for implementing various microservice patterns and technologies.

## Architecture Overview
![diagram](https://github.com/Millenium47/order-microservices/assets/17088629/4ff3ccd4-6799-47a9-a293-06dd373d1554)

### Services:
- **Eureka Server**: Service registry for microservices to locate each other.
- **API Gateway**: The entry point for our microservices which authenticates requests via JWT tokens.
- **Identity Service**: Manages user authentication and token generation.
- **Product Service**: Handles product-related operations.
- **Order Service**: Manages order processing and validation.
- **Inventory Service**: Keeps track of stock and inventory levels.
- **Notification Service**: Sends out notifications via Kafka messaging.

## TODO
- [ ] **Circuit Breaker/Rate Limiting**
- [ ] **Notification Service with Kafka Messaging**
- [ ] **Distributed Tracing Implementation**
- [ ] **Containerization and Orchestration (K8s)**

