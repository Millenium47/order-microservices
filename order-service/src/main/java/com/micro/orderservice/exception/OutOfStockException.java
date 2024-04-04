package com.micro.orderservice.exception;

import com.micro.orderservice.dto.InventoryCheckResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class OutOfStockException extends RuntimeException {
    private final List<InventoryCheckResponse> outOfStockItems;

    public OutOfStockException(String message, List<InventoryCheckResponse> outOfStockItems) {
        super(message);
        this.outOfStockItems = outOfStockItems;
    }

    public List<InventoryCheckResponse> getOutOfStockItems() {
        return outOfStockItems;
    }
}
