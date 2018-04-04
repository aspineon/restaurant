package com.nibado.restaurant.service.kitchen.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties
@Getter
public class RecipeConfiguration {
    private final List<Recipe> recipes = new ArrayList<>();

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Recipe {
        private String name;
        private int duration;
        private List<ItemConfiguration> items;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ItemConfiguration {
        private String name;
        private int qtty;
    }
}
