package com.micro.productservice.service;

import com.micro.productservice.dto.ProductRequest;
import com.micro.productservice.exception.NotFoundException;
import com.micro.productservice.model.Product;
import com.micro.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    private Product testProduct;

    @BeforeEach
    void setUp() {
        testProduct = new Product(1L, "Test", "Description", new BigDecimal("10.0"));
    }

    @Test
    void createProduct(){
        ProductRequest request = new ProductRequest("Test", "Description", new BigDecimal("10.0"));

        when(productRepository.save(any(Product.class))).thenReturn(testProduct);

        var response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals(1L, response.id());
        assertEquals("Test", response.name());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProducts(){
        when(productRepository.findAll()).thenReturn(List.of(testProduct));

        var responses = productService.getAllProducts();

        assertNotNull(responses);
        assertEquals(1, responses.size());
        assertEquals(1L, responses.getFirst().id());
        assertEquals("Test", responses.getFirst().name());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void getProductById(){
        when(productRepository.findById(anyLong())).thenReturn(Optional.ofNullable(testProduct));

        var response = productService.getProductById(1L);

        assertNotNull(response);
        assertEquals(response.description(), testProduct.getDescription());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void getProductByIdException() {
        when(productRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getProductById(1L))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("Product with identifier 1 not found");
    }
}
