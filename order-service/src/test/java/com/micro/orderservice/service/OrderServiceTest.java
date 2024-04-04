package com.micro.orderservice.service;

import com.micro.orderservice.client.InventoryClient;
import com.micro.orderservice.dto.InventoryCheckResponse;
import com.micro.orderservice.dto.ItemDto;
import com.micro.orderservice.dto.OrderRequest;
import com.micro.orderservice.exception.OutOfStockException;
import com.micro.orderservice.model.Order;
import com.micro.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private InventoryClient inventoryClient;

    private ItemDto itemDto;

    @BeforeEach
    void setUp() {
        itemDto = new ItemDto(1L, "04c80e23-360d-4e72-ab7a-6b5d13ee83e3", new BigDecimal("29.99"), 2);
    }

    @Test
    void testPlaceOrder_Successful() {
        OrderRequest orderRequest = new OrderRequest(List.of(itemDto));

        when(inventoryClient.isInStock(any(List.class)))
                .thenReturn(Collections.singletonList(new InventoryCheckResponse(itemDto.productCode(),itemDto.quantity(), true)));

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Order placedOrder = orderService.placeOrder(orderRequest);

        assertThat(placedOrder.getOrderedItems()).hasSize(1);
        assertThat(placedOrder.getOrderedItems().getFirst().getProductCode()).isEqualTo(itemDto.productCode());
        assertThat(placedOrder.getOrderedItems().getFirst().getPrice()).isEqualByComparingTo(itemDto.price());
        assertThat(placedOrder.getOrderedItems().getFirst().getQuantity()).isEqualTo(itemDto.quantity());
        assertThat(placedOrder.getOrderNumber()).isNotNull();

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(inventoryClient, times(1)).isInStock(any(List.class));
    }

    @Test
    void testPlaceOrder_ThrowsException() {
        OrderRequest orderRequest = new OrderRequest(List.of(itemDto));

        when(inventoryClient.isInStock(any(List.class)))
                .thenReturn(Collections.singletonList(new InventoryCheckResponse(itemDto.productCode(), itemDto.quantity(), false)));

        assertThatThrownBy(() -> orderService.placeOrder(orderRequest))
                .isInstanceOf(OutOfStockException.class)
                .hasMessageContaining("Some items are out of stock.");

        verify(inventoryClient, times(1)).isInStock(any(List.class));
        verifyNoInteractions(orderRepository);
    }
}
