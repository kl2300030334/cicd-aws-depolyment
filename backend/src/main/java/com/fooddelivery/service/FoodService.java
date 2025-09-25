package com.fooddelivery.service;

import com.fooddelivery.entity.Food;
import com.fooddelivery.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {
    
    @Autowired
    private FoodRepository foodRepository;
    
    public List<Food> getAllFoods() {
        return foodRepository.findByIsAvailableTrue();
    }
    
    public List<Food> getFoodsByCategory(String category) {
        return foodRepository.findByCategoryAndIsAvailableTrue(category);
    }
    
    public List<String> getCategories() {
        return foodRepository.findDistinctCategories();
    }
    
    public List<Food> searchFoods(String name) {
        return foodRepository.findByNameContainingAndIsAvailableTrue(name);
    }
    
    public Optional<Food> getFoodById(Long id) {
        return foodRepository.findById(id);
    }
    
    public Food createFood(Food food) {
        return foodRepository.save(food);
    }
    
    public Food updateFood(Long id, Food foodDetails) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        
        food.setName(foodDetails.getName());
        food.setDescription(foodDetails.getDescription());
        food.setPrice(foodDetails.getPrice());
        food.setImageUrl(foodDetails.getImageUrl());
        food.setCategory(foodDetails.getCategory());
        food.setIsAvailable(foodDetails.getIsAvailable());
        
        return foodRepository.save(food);
    }
    
    public void deleteFood(Long id) {
        Food food = foodRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food not found"));
        
        foodRepository.delete(food);
    }
}



