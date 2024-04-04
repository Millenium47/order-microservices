package com.micro.orderservice.client;

import com.micro.orderservice.dto.InventoryCheckRequest;
import com.micro.orderservice.dto.InventoryCheckResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "INVENTORY-SERVICE")
public interface InventoryClient {
    @RequestMapping(method = RequestMethod.POST, value = "/api/v1/inventory")
    List<InventoryCheckResponse> isInStock(@RequestBody List<InventoryCheckRequest> requests);
}
