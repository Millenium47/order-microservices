package com.micro.orderservice.dto;

public record InventoryCheckResponse(String productCode, Integer quantity, boolean inStock) {
}
