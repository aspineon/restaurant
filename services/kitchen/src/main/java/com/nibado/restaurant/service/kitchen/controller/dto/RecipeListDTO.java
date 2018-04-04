package com.nibado.restaurant.service.kitchen.controller.dto;

import lombok.Value;

import java.util.List;

@Value
public class RecipeListDTO {
    private List<RecipeDTO> recipes;
}
