package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.models.Item;
import com.guidopierri.pantrybe.services.ItemService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;
    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }
    @GetMapping("")
    public List<ItemDto> getItems() {

        return itemService.getItems();
    }
}
