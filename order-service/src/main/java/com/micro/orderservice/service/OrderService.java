package com.micro.orderservice.service;

import com.micro.orderservice.client.InventoryClient;
import com.micro.orderservice.dto.InventoryCheckRequest;
import com.micro.orderservice.dto.InventoryCheckResponse;
import com.micro.orderservice.dto.ItemDto;
import com.micro.orderservice.dto.OrderRequest;
import com.micro.orderservice.exception.OutOfStockException;
import com.micro.orderservice.model.Item;
import com.micro.orderservice.model.Order;
import com.micro.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    @Autowired
    public OrderService(OrderRepository orderRepository, InventoryClient inventoryClient) {
        this.orderRepository = orderRepository;
        this.inventoryClient = inventoryClient;
    }

    @Transactional
    public Order placeOrder(OrderRequest request) {
        Order order = mapOrder(request);

        List<InventoryCheckRequest> checkRequests = request.orderedItems().stream()
                .map(item -> new InventoryCheckRequest(item.productCode(), item.quantity()))
                .toList();

        List<InventoryCheckResponse> outOfStockItems = inventoryClient.isInStock(checkRequests)
                .stream().filter(response -> !response.inStock())
                .toList();

        if (!outOfStockItems.isEmpty()) {
            throw new OutOfStockException("Some items are out of stock.", outOfStockItems);
        }

        return orderRepository.save(order);
    }

    private Order mapOrder(OrderRequest request){
        Order order = new Order();
        request.orderedItems()
                .stream()
                .map(this::mapItem)
                .forEach(order::addOrderedItems);
        order.setOrderNumber(UUID.randomUUID().toString());

        return order;
    }

    private Item mapItem(ItemDto dto){
        Item item = new Item();
        item.setProductCode(dto.productCode());
        item.setPrice(dto.price());
        item.setQuantity(dto.quantity());
        return item;
    }
}
