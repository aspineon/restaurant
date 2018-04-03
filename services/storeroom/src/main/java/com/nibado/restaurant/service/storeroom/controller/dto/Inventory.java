package com.nibado.restaurant.service.storeroom.controller.dto;

import lombok.Value;

import java.util.List;

@Value
public class Inventory {
    private List<Item> items;


    @Value
    public static class Item {
        private String name;
        private int qtty;
    }

    public static Item item(final String name, final int qtty) {
        return new Item(name, qtty);
    }
}
