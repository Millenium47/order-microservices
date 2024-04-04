package com.micro.orderservice.dto;

import java.util.List;

public record OrderRequest(List<ItemDto> orderedItems) {
}
