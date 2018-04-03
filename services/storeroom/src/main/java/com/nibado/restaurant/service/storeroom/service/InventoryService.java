package com.nibado.restaurant.service.storeroom.service;

import com.nibado.restaurant.service.storeroom.repository.ItemRepository;
import com.nibado.restaurant.service.storeroom.service.domain.Item;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryService {
    private final ItemRepository itemRepository;

    public InventoryService(final ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public List<Item> getAll() {
        return itemRepository.getAll().stream().map(e -> new Item(e.getName(), (int) e.getQtty())).collect(Collectors.toList());
    }
}
