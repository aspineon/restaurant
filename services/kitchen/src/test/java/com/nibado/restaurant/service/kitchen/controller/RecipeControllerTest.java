package com.nibado.restaurant.service.kitchen.controller;

import com.nibado.restaurant.service.kitchen.configuration.RecipeConfiguration;
import com.nibado.restaurant.service.kitchen.service.RecipeService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RecipeControllerTest {
    private MockMvc mockMvc;

    private RecipeService recipeService;

    @Before
    public void setup() {
        recipeService = mock(RecipeService.class);
        RecipeController controller = new RecipeController(recipeService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller)
                .setControllerAdvice(new ErrorHandlers()).build();
    }

    @Test
    public void recipes() throws Exception {
        when(recipeService.getRecipes()).thenReturn(Arrays.asList(
                recipe("fries", 2, "potatoes"),
                recipe("coke", 1, "coke")));

        mockMvc.perform(get("/recipe"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipes[0].name", is("fries")));
    }

    public static RecipeConfiguration.Recipe recipe(String name, int duration, String... ingredients) {
        List<RecipeConfiguration.ItemConfiguration> items = new ArrayList<>();

        for(int i = 0;i < ingredients.length;i++) {
            items.add(new RecipeConfiguration.ItemConfiguration(ingredients[i], i + 1));
        }

        return new RecipeConfiguration.Recipe(name, duration, items);
    }
}