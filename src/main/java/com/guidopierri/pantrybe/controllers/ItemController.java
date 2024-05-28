package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.services.ItemService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @Operation(summary = "Get all items")
    @GetMapping("")
    public List<ItemDto> getItems() {

        return itemService.getItems();
    }
}
