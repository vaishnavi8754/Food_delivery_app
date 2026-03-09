package com.fooddelivery.model;

/**
 * Model class for Cuisine with statistics
 */
public class Cuisine {
    private String name;
    private int restaurantCount;
    private int dishCount;
    private String imageUrl;

    public Cuisine() {
    }

    public Cuisine(String name, int restaurantCount, int dishCount, String imageUrl) {
        this.name = name;
        this.restaurantCount = restaurantCount;
        this.dishCount = dishCount;
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRestaurantCount() {
        return restaurantCount;
    }

    public void setRestaurantCount(int restaurantCount) {
        this.restaurantCount = restaurantCount;
    }

    public int getDishCount() {
        return dishCount;
    }

    public void setDishCount(int dishCount) {
        this.dishCount = dishCount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
