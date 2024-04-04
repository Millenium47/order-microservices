package com.micro.inventoryservice.model;

import jakarta.persistence.*;

@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String productCode;
    @Column(nullable = false)
    private Integer quantity;


}
