package com.fitplanner.authentication.model.food;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private String name;
    private List<FoodItem> foodItems;

    public Meal(String name) {
        this.name = name;
        this.foodItems = new ArrayList<>();
    }

    // getters
    public String getName() { return name; }
    public List<FoodItem> getFoodItems() { return foodItems; }
}