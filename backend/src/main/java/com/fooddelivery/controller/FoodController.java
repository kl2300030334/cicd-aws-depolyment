package com.fooddelivery.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fooddelivery.entity.Food;
import com.fooddelivery.service.FoodService;

@RestController
@RequestMapping("api/food")
@CrossOrigin(origins = "http://ec2-98-84-110-225.compute-1.amazonaws.com:3000", allowCredentials = "true")
public class FoodController {

    @Autowired
    private FoodService foodService;

    // DTO class for sending clean JSON
    public static class FoodDTO {
        private Long id;
        private String name;
        private String description;
        private Double price;
        private String imageUrl;
        private String category;

        public FoodDTO(Food food) {
            this.id = food.getId();
            this.name = food.getName();
            this.description = food.getDescription();
            this.price = food.getPrice();
            this.imageUrl = food.getImageUrl();
            this.category = food.getCategory();
        }

        // Getters
        public Long getId() { return id; }
        public String getName() { return name; }
        public String getDescription() { return description; }
        public Double getPrice() { return price; }
        public String getImageUrl() { return imageUrl; }
        public String getCategory() { return category; }
    }

    @GetMapping("list")
    public ResponseEntity<List<FoodDTO>> getAllFoods() {
        try {
            List<FoodDTO> foods = foodService.getAllFoods()
                    .stream()
                    .map(FoodDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("categories")
    public ResponseEntity<List<String>> getCategories() {
        try {
            List<String> categories = foodService.getCategories();
            return ResponseEntity.ok(categories);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<FoodDTO>> getFoodsByCategory(@PathVariable String category) {
        try {
            List<FoodDTO> foods = foodService.getFoodsByCategory(category)
                    .stream()
                    .map(FoodDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("search")
    public ResponseEntity<List<FoodDTO>> searchFoods(@RequestParam String name) {
        try {
            List<FoodDTO> foods = foodService.searchFoods(name)
                    .stream()
                    .map(FoodDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(foods);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}






