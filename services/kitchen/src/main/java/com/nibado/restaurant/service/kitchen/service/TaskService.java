package com.nibado.restaurant.service.kitchen.service;

import com.nibado.restaurant.service.kitchen.configuration.RecipeConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
public class TaskService {
    private final ExecutorService pool = Executors.newFixedThreadPool(2);

    private final RecipeService recipeService;

    public TaskService(final RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    public void submit(final String recipe) {
        log.debug("Task for recipe  {} submitted", recipe);

        pool.submit(() -> cook(recipe));
    }

    private void cook(final String name) {
        log.info("Cooking '{}'", name);

        Optional<RecipeConfiguration.Recipe> recipe = recipeService.find(name);

        if(recipe.isPresent()) {
            work(recipe.get().getDuration());
        } else {
            log.warn("No recipe found with name '{}'");
        }
    }

    private void work(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {}
    }
}
