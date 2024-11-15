package com.hexaware.app.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.app.Dao.FoodItemRepository;
import com.hexaware.app.Dto.FoodItem;


@Service
public class FoodItemService {
    
    @Autowired
    private FoodItemRepository foodItemRepository;

    // Retrieve all food items
    public List<FoodItem> getAllFoodItems() {
        return foodItemRepository.findAll();
    }

    // Add a new food item (for admin)
    public FoodItem addFoodItem(FoodItem foodItem) {
        return foodItemRepository.save(foodItem);
    }

    // Retrieve a food item by ID
    public FoodItem getFoodItem(Long id) {
        return foodItemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food item not found with ID: " + id));
    }

    // Update an existing food item
    public FoodItem updateFoodItem(Long id, FoodItem updatedItem) {
        FoodItem existingItem = getFoodItem(id);
        existingItem.setName(updatedItem.getName());
        existingItem.setCategory(updatedItem.getCategory());
        existingItem.setPrice(updatedItem.getPrice());
        existingItem.setQuantityAvailable(updatedItem.getQuantityAvailable());
        return foodItemRepository.save(existingItem);
    }

    // Delete a food item by ID
    public void deleteFoodItem(Long id) {
        FoodItem item = getFoodItem(id);
        foodItemRepository.delete(item);
    }

    public List<FoodItem> searchFoodItems(String searchTerm) {
        return foodItemRepository.findByCategoryOrHotelName(searchTerm);
    }
}