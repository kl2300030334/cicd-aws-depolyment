package com.fooddelivery.dto;

import com.fooddelivery.entity.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderRequest {
    
    @Valid
    @NotNull(message = "Address is required")
    private Address address;
    
    @NotEmpty(message = "Items are required")
    private List<OrderItemRequest> items;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Amount must be greater than 0")
    private Double amount;
    
    // Constructors
    public OrderRequest() {}
    
    public OrderRequest(Address address, List<OrderItemRequest> items, Double amount) {
        this.address = address;
        this.items = items;
        this.amount = amount;
    }
    
    // Getters and Setters
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public List<OrderItemRequest> getItems() {
        return items;
    }
    
    public void setItems(List<OrderItemRequest> items) {
        this.items = items;
    }
    
    public Double getAmount() {
        return amount;
    }
    
    public void setAmount(Double amount) {
        this.amount = amount;
    }
    
    // Inner class for order items
    public static class OrderItemRequest {
        private Long id;
        private String name;
        private Double price;
        private String description;
        private String image;
        private Integer quantity;
        
        // Constructors
        public OrderItemRequest() {}
        
        public OrderItemRequest(Long id, String name, Double price, String description, String image, Integer quantity) {
            this.id = id;
            this.name = name;
            this.price = price;
            this.description = description;
            this.image = image;
            this.quantity = quantity;
        }
        
        // Getters and Setters
        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public Double getPrice() {
            return price;
        }
        
        public void setPrice(Double price) {
            this.price = price;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
        
        public String getImage() {
            return image;
        }
        
        public void setImage(String image) {
            this.image = image;
        }
        
        public Integer getQuantity() {
            return quantity;
        }
        
        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }
    }
}


