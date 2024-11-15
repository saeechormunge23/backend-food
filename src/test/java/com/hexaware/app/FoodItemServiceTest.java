package com.hexaware.app;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.hexaware.app.Dao.FoodItemRepository;
import com.hexaware.app.Dto.FoodItem;
import com.hexaware.app.Service.FoodItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class FoodItemServiceTest {

    @InjectMocks
    private FoodItemService foodItemService;

    @Mock
    private FoodItemRepository foodItemRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllFoodItems_Success() {
        FoodItem item1 = new FoodItem();
        FoodItem item2 = new FoodItem();

        when(foodItemRepository.findAll()).thenReturn(Arrays.asList(item1, item2));

        List<FoodItem> foodItems = foodItemService.getAllFoodItems();

        assertEquals(2, foodItems.size());
        verify(foodItemRepository, times(1)).findAll();
    }

    @Test
    void testAddFoodItem_Success() {
        FoodItem foodItem = new FoodItem();
        foodItem.setName("Burger");
        foodItem.setPrice(5.99);

        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(foodItem);

        FoodItem addedItem = foodItemService.addFoodItem(foodItem);

        assertNotNull(addedItem);
        assertEquals("Burger", addedItem.getName());
        verify(foodItemRepository, times(1)).save(foodItem);
    }

    @Test
    void testGetFoodItem_Success() {
        FoodItem foodItem = new FoodItem();
        foodItem.setId(1L);

        when(foodItemRepository.findById(1L)).thenReturn(Optional.of(foodItem));

        FoodItem fetchedItem = foodItemService.getFoodItem(1L);

        assertNotNull(fetchedItem);
        assertEquals(1L, fetchedItem.getId());

        verify(foodItemRepository, times(1)).findById(1L);
    }

    @Test
    void testGetFoodItem_NotFound() {
        when(foodItemRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            foodItemService.getFoodItem(1L);
        });

        assertEquals("Food item not found with ID: 1", exception.getMessage());
    }

    @Test
    void testUpdateFoodItem_Success() {
        FoodItem existingItem = new FoodItem();
        existingItem.setId(1L);
        existingItem.setName("Pizza");

        FoodItem updatedItem = new FoodItem();
        updatedItem.setName("Burger");
        updatedItem.setPrice(7.99);

        when(foodItemRepository.findById(1L)).thenReturn(Optional.of(existingItem));
        when(foodItemRepository.save(any(FoodItem.class))).thenReturn(existingItem);

        FoodItem result = foodItemService.updateFoodItem(1L, updatedItem);

        assertEquals("Burger", result.getName());
        assertEquals(7.99, result.getPrice());
        verify(foodItemRepository, times(1)).save(existingItem);
    }

    @Test
    void testUpdateFoodItem_NotFound() {
        FoodItem updatedItem = new FoodItem();

        when(foodItemRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            foodItemService.updateFoodItem(1L, updatedItem);
        });

        assertEquals("Food item not found with ID: 1", exception.getMessage());
    }

    @Test
    void testDeleteFoodItem_Success() {
        FoodItem item = new FoodItem();
        item.setId(1L);

        when(foodItemRepository.findById(1L)).thenReturn(Optional.of(item));

        foodItemService.deleteFoodItem(1L);

        verify(foodItemRepository, times(1)).delete(item);
    }

    @Test
    void testDeleteFoodItem_NotFound() {
        when(foodItemRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            foodItemService.deleteFoodItem(1L);
        });

        assertEquals("Food item not found with ID: 1", exception.getMessage());
    }
}