package com.nibado.restaurant.service.kitchen.service;

import com.nibado.restaurant.service.kitchen.configuration.RecipeConfiguration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    private final List<RecipeConfiguration.Recipe> recipes;

    public RecipeService(final RecipeConfiguration recipeConfiguration) {
        this.recipes = recipeConfiguration.getRecipes();
    }

    public List<RecipeConfiguration.Recipe> getRecipes() {
        return recipes;
    }

    public Optional<RecipeConfiguration.Recipe> find(final String name) {
        return recipes.stream().filter(r -> r.getName().equals(name)).findAny();
    }
}
