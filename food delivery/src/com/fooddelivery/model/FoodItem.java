package com.fooddelivery.model;

import java.sql.Timestamp;

/**
 * FoodItem Model - Java Bean representing a food item
 */
public class FoodItem {
    private int foodId;
    private int restaurantId;
    private String restaurantName;
    private String name;
    private String description;
    private double price;
    private String imageUrl;
    private String category;
    private boolean isVegetarian;
    private boolean isAvailable;
    private Timestamp createdAt;
    private Timestamp updatedAt;
    private double rating;
    private int ratingCount;
    private boolean isBestseller;
    private String itemTag;

    public FoodItem() {
    }

    public FoodItem(int restaurantId, String name, String description, double price, String category) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.category = category;
        this.isAvailable = true;
    }

    // Getters and Setters
    public int getFoodId() {
        return foodId;
    }

    public void setFoodId(int foodId) {
        this.foodId = foodId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isVegetarian() {
        return isVegetarian;
    }

    public void setVegetarian(boolean isVegetarian) {
        this.isVegetarian = isVegetarian;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public boolean isBestseller() {
        return isBestseller;
    }

    public void setBestseller(boolean bestseller) {
        isBestseller = bestseller;
    }

    public String getItemTag() {
        return itemTag;
    }

    public void setItemTag(String itemTag) {
        this.itemTag = itemTag;
    }
}
