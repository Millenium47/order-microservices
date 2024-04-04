package com.micro.orderservice.dto;

public record InventoryCheckRequest(String productCode, Integer quantity) {
}
