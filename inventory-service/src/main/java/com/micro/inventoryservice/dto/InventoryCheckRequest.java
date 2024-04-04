package com.micro.inventoryservice.dto;

public record InventoryCheckRequest(String productCode, Integer quantity) {
}
