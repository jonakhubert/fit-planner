package com.fitplanner.nutrition.controller;

import com.fitplanner.nutrition.model.api.ConfirmationResponse;
import com.fitplanner.nutrition.model.api.MealRequest;
import com.fitplanner.nutrition.model.food.DailyMealPlan;
import com.fitplanner.nutrition.service.NutritionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/nutrition")
public class NutritionController {

    private final NutritionService nutritionService;

    @Autowired
    public NutritionController(NutritionService nutritionService) {
        this.nutritionService = nutritionService;
    }

    @PostMapping(
        path = "/add-food-item",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ConfirmationResponse> addFoodItem(
        @RequestBody @Valid MealRequest request,
        @RequestHeader("Authorization") String header
    ) {
        return ResponseEntity.ok(nutritionService.addFoodItem(request, header));
    }

    @PostMapping(
        path = "/remove-food-item",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<ConfirmationResponse> removeFoodItem(
        @RequestBody @Valid MealRequest request,
        @RequestHeader("Authorization") String header
    ) {
        return ResponseEntity.ok(nutritionService.removeFoodItem(request, header));
    }

    @GetMapping(
        path = "/daily-meal-plan",
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DailyMealPlan> getDailyMealPlan(
        @RequestParam("email") String email,
        @RequestParam("date") String date,
        @RequestHeader("Authorization") String header
    ) {
        return ResponseEntity.ok(nutritionService.getDailyMealPlan(email, date, header));
    }
}
