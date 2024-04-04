package com.micro.inventoryservice.service;

import com.micro.inventoryservice.dto.InventoryCheckRequest;
import com.micro.inventoryservice.dto.InventoryCheckResponse;
import com.micro.inventoryservice.repository.InventoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@SpringBootTest
public class InventoryServiceTest {

    @Autowired
    private InventoryService inventoryService;

    @MockBean
    private InventoryRepository inventoryRepository;

    private List<InventoryCheckRequest> requests;

    @BeforeEach
    void setUp() {
        requests = List.of(
                new InventoryCheckRequest("productCodeTrue", 10),
                new InventoryCheckRequest("productCodeFalse", 5)
        );

        // Configure mocked responses
        given(inventoryRepository.existsByProductCodeAndQuantityIsGreaterThanEqual("productCodeTrue", 10)).willReturn(true);
        given(inventoryRepository.existsByProductCodeAndQuantityIsGreaterThanEqual("productCodeFalse", 5)).willReturn(false);
    }

    @Test
    void isInStock() {
        List<InventoryCheckResponse> responses = inventoryService.isInStock(requests);

        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).productCode()).isEqualTo("productCodeTrue");
        assertThat(responses.get(0).inStock()).isTrue();
        assertThat(responses.get(1).productCode()).isEqualTo("productCodeFalse");
        assertThat(responses.get(1).inStock()).isFalse();
    }
}
