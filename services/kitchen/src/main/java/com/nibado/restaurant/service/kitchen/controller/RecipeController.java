package com.nibado.restaurant.service.kitchen.controller;

import com.nibado.restaurant.service.kitchen.controller.dto.RecipeDTO;
import com.nibado.restaurant.service.kitchen.controller.dto.RecipeListDTO;
import com.nibado.restaurant.service.kitchen.service.RecipeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/recipe")
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    public RecipeListDTO recipes() {
        List<RecipeDTO> recipes = recipeService.getRecipes()
                .stream()
                .map(rc -> new RecipeDTO(rc.getName(), rc.getDuration()))
                .collect(Collectors.toList());

        return new RecipeListDTO(recipes);
    }
}
