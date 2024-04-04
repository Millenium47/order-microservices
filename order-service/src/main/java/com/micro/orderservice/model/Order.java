package com.micro.orderservice.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> orderedItems = new ArrayList<>();

    public Order() {}

    public Order(Long id, String orderNumber, List<Item> orderedItems) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderedItems = orderedItems;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<Item> getOrderedItems() {
        return orderedItems;
    }

    public void addOrderedItems(Item medicalHistory) {
        medicalHistory.setOrder(this);
        this.orderedItems.add(medicalHistory);
    }

    public void setOrderedItems(List<Item> medicalHistory) {
        orderedItems.forEach(this::addOrderedItems);
    }
}
