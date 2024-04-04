package com.micro.inventoryservice.service;

import com.micro.inventoryservice.dto.InventoryCheckRequest;
import com.micro.inventoryservice.dto.InventoryCheckResponse;
import com.micro.inventoryservice.repository.InventoryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    @Transactional(readOnly = true)
    public List<InventoryCheckResponse> isInStock(List<InventoryCheckRequest> requests) {
        return requests.stream()
                .map(request -> new InventoryCheckResponse(
                        request.productCode(),
                        request.quantity(),
                        inventoryRepository.existsByProductCodeAndQuantityIsGreaterThanEqual(request.productCode(), request.quantity())))
                .collect(Collectors.toList());
    }
}