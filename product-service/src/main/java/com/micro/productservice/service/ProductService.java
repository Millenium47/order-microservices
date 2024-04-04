package com.micro.productservice.service;

import com.micro.productservice.dto.ProductRequest;
import com.micro.productservice.dto.ProductResponse;
import com.micro.productservice.exception.NotFoundException;
import com.micro.productservice.model.Product;
import com.micro.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        Product mappedProduct = new Product(productRequest.name(), productRequest.description(), productRequest.price());
        Product product = productRepository.save(mappedProduct);
        logger.info("Product created successfully with ID: {}", product.getId());
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }

    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll().stream().map(product -> new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice())).collect(Collectors.toList());
    }

    public ProductResponse getProductById(Long id) {
        Product product =  productRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Product with identifier %s not found", id)));
        return new ProductResponse(product.getId(), product.getName(), product.getDescription(), product.getPrice());
    }
}
