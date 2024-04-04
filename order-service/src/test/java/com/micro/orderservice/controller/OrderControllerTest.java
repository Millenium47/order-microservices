package com.micro.orderservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.orderservice.dto.ItemDto;
import com.micro.orderservice.dto.OrderRequest;
import com.micro.orderservice.model.Order;
import com.micro.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void placeOrderTest() throws Exception {
        Order serviceReturn = new Order();
        serviceReturn.setId(1L);
        serviceReturn.setOrderNumber("04c80e23-360d-4e72-ab7a-6b5d13ee83e3");

        OrderRequest request = new OrderRequest(List.of(new ItemDto(1L, "Iphone 15", new BigDecimal("1200.0"),2)));
        when(orderService.placeOrder(any(OrderRequest.class))).thenReturn(serviceReturn);

        mockMvc.perform(post("/api/v1/orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.orderNumber").value(serviceReturn.getOrderNumber()));
    }

}
