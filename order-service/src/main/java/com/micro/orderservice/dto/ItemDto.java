package com.micro.orderservice.dto;

import java.math.BigDecimal;

public record ItemDto(Long id, String productCode, BigDecimal price, Integer quantity) {
}
