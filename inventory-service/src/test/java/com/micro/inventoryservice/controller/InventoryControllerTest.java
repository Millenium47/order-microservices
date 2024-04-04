package com.micro.inventoryservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.micro.inventoryservice.dto.InventoryCheckRequest;
import com.micro.inventoryservice.dto.InventoryCheckResponse;
import com.micro.inventoryservice.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InventoryController.class)
public class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void isInStock() throws Exception {
        List<InventoryCheckRequest> requests = List.of(new InventoryCheckRequest("productCode123", 10));
        List<InventoryCheckResponse> responses = List.of(new InventoryCheckResponse("productCode123", 10,true));

        given(inventoryService.isInStock(requests)).willReturn(responses);

        mockMvc.perform(post("/api/v1/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requests)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].productCode", is("productCode123")))
                .andExpect(jsonPath("$[0].inStock", is(true)));
    }
}
