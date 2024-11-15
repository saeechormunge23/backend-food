package com.hexaware.app.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.app.Dto.FoodItem;
import com.hexaware.app.Service.FoodItemService;


@RestController
@RequestMapping("/api/food")
public class FoodItemController {

    @Autowired
    private FoodItemService foodItemService;

    // Get all food items
    @GetMapping("/items")
    public ResponseEntity<List<FoodItem>> getAllFoodItems() {
        List<FoodItem> items = foodItemService.getAllFoodItems();
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    // Add a new food item (for admin)
    @PostMapping("/add")
    public ResponseEntity<FoodItem> addFoodItem(@RequestBody FoodItem foodItem) {
    	System.out.println(foodItem);
        FoodItem newItem = foodItemService.addFoodItem(foodItem);
        return new ResponseEntity<>(newItem, HttpStatus.CREATED);
    }

    // Get a food item by ID
    @GetMapping("/item/{id}")
    public ResponseEntity<FoodItem> getFoodItemById(@PathVariable Long id) {
        FoodItem item = foodItemService.getFoodItem(id);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    // Update an existing food item
    @PutMapping("/update/{id}")
    public ResponseEntity<FoodItem> updateFoodItem(@PathVariable Long id, @RequestBody FoodItem updatedItem) {
        FoodItem item = foodItemService.updateFoodItem(id, updatedItem);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    // Delete a food item by ID 
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteFoodItem(@PathVariable Long id) {
        foodItemService.deleteFoodItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    //search by hotel name or category
    @GetMapping("/search/{searchTerm}")
    public ResponseEntity<List<FoodItem>> searchFoodItems(@PathVariable String searchTerm) {
        List<FoodItem> items = foodItemService.searchFoodItems(searchTerm);
        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    
}