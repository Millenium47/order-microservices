package com.micro.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.productservice.dto.ProductRequest;
import com.micro.productservice.dto.ProductResponse;
import com.micro.productservice.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ProductResponse testProductResponse;

    private final Long productId = 1L;
    private final ProductRequest productRequest = new ProductRequest("Test Product", "Description", new BigDecimal("10.0"));
    private final ProductResponse productResponse = new ProductResponse(productId, "Test Product", "Description", new BigDecimal("10.0"));

    @Test
    void createProduct() throws Exception {
        when(productService.createProduct(any(ProductRequest.class))).thenReturn(productResponse);

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(productRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService).createProduct(any(ProductRequest.class));
    }

    @Test
    void getAllProducts() throws Exception {
        List<ProductResponse> productResponses = List.of(productResponse);
        when(productService.getAllProducts()).thenReturn(productResponses);

        mockMvc.perform(get("/api/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Test Product"));

        verify(productService).getAllProducts();
    }

    @Test
    void getProductById() throws Exception {
        when(productService.getProductById(productId)).thenReturn(productResponse);

        mockMvc.perform(get("/api/v1/products/{id}", productId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(productId))
                .andExpect(jsonPath("$.name").value("Test Product"));

        verify(productService).getProductById(productId);
    }
}
