package com.fitplanner.nutrition.model.api;

import com.fitplanner.nutrition.model.food.FoodItem;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record FoodItemCreationRequest(
        @NotNull @NotEmpty String email,
        @NotNull @NotEmpty String date,
        @NotNull @NotEmpty String mealName,
        @NotNull FoodItem foodItem,
        @NotNull int calories,
        @NotNull double protein,
        @NotNull double fat,
        @NotNull double carbs
) {}