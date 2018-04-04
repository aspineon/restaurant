package com.nibado.restaurant.service.storeroom.controller;

import com.nibado.restaurant.service.storeroom.controller.dto.Inventory;
import com.nibado.restaurant.service.storeroom.service.InventoryService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import static com.nibado.restaurant.service.storeroom.controller.dto.Inventory.item;

@RestController
@RequestMapping("/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(final InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public Inventory inventory() {
        return new Inventory(inventoryService.getAll().stream().map(i -> item(i.getName(), i.getQtty())).collect(Collectors.toList()));
    }
}
