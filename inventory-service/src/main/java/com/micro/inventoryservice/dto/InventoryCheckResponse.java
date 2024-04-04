package com.micro.inventoryservice.dto;

public record InventoryCheckResponse(String productCode, Integer quantity, boolean inStock) {
}
