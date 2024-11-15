package com.hexaware.app.Dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;


@Entity
public class FoodItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Add auto-increment for IDs
    private Long id;
    
    @NotEmpty
    private String name;
    
    private String category;
    
    private Double price;
    
    private int quantityAvailable;
    
    private String foodImage;
    
    private String hotelName;

    // Constructors, getters, setters, equals, hashCode, and toString methods

    public FoodItem() {}

    public FoodItem(Long id, String name, String category, Double price, int quantityAvailable,String foodImage) {
        this.id = id;
        this.name = name;
        this.setCategory(category);
        this.setPrice(price);
        this.setQuantityAvailable(quantityAvailable);
        this.setFoodImage(foodImage);     
    }
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getQuantityAvailable() {
		return quantityAvailable;
	}

	public void setQuantityAvailable(int quantityAvailable) {
		this.quantityAvailable = quantityAvailable;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Long getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public String getFoodImage() {
		return foodImage;
	}

	public void setFoodImage(String foodImage) {
		this.foodImage = foodImage;
	}

	public void setId(long l) {
		this.id=l;
		
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}

}
