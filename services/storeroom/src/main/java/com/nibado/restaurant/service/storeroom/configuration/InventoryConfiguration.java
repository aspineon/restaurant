package com.nibado.restaurant.service.storeroom.configuration;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties
@Getter
public class InventoryConfiguration {
    private final List<ItemConfiguration> inventory = new ArrayList<>();

    @Data
    public static class ItemConfiguration {
        private String name;
        private int qtty;
    }
}
