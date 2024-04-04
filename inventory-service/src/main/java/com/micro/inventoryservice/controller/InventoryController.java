package com.micro.inventoryservice.controller;

import com.micro.inventoryservice.dto.InventoryCheckRequest;
import com.micro.inventoryservice.dto.InventoryCheckResponse;
import com.micro.inventoryservice.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @Autowired
    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryCheckResponse> isInStock(@RequestBody List<InventoryCheckRequest> requests) {
        return inventoryService.isInStock(requests);
    }
}
