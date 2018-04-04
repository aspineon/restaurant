package com.nibado.restaurant.service.storeroom.component;

import com.nibado.restaurant.service.storeroom.configuration.InventoryConfiguration;
import com.nibado.restaurant.service.storeroom.service.InventoryService;
import com.nibado.restaurant.service.storeroom.service.domain.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@Slf4j
public class Shopper {
    private final InventoryService inventoryService;
    private final List<InventoryConfiguration.ItemConfiguration> itemConfigurations;

    public Shopper(final InventoryService inventoryService, final InventoryConfiguration inventoryConfiguration) {
        this.inventoryService = inventoryService;
        this.itemConfigurations = inventoryConfiguration.getInventory();
    }

    @Scheduled(fixedRateString = "${shopper.rate:30000}")
    public void checkInventory() {
        Map<String, Integer> currentStore = inventoryService.getAll().stream().collect(Collectors.toMap(Item::getName, Item::getQtty));

        List<Item> required = new ArrayList<>();

        for (InventoryConfiguration.ItemConfiguration ic : itemConfigurations) {
            if (!currentStore.containsKey(ic.getName())) {
                required.add(new Item(ic.getName(), ic.getQtty()));
            } else if(currentStore.get(ic.getName()) < ic.getQtty()) {
                required.add(new Item(ic.getName(), ic.getQtty() - currentStore.get(ic.getName())));
            }
        }

        required.forEach(i -> {
            log.info("Buying {} {}", i.getQtty(), i.getName());

            inventoryService.increment(i.getName(), i.getQtty());
        });
    }
}
