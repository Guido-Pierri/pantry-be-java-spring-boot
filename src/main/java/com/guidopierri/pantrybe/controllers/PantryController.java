package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.requests.CreateItemRequest;
import com.guidopierri.pantrybe.dtos.requests.CreatePantryRequest;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.Item;

import com.guidopierri.pantrybe.services.ItemService;
import com.guidopierri.pantrybe.services.PantryService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/pantry")
public class PantryController {
    private final PantryService pantryService;
    private final ItemService itemService;
    private final EntityMapper entityMapper;
    public PantryController(PantryService pantryService, ItemService itemService, EntityMapper entityMapper) {
        this.pantryService = pantryService;
        this.itemService = itemService;
        this.entityMapper = entityMapper;
    }
    @GetMapping("")
    public List<Pantry> getPantries() {
        return pantryService.getAllPantries();
    }
    @GetMapping("/{id}")
    public PantryDto getPantry(@PathVariable long id) {
        return entityMapper.pantryToPantryDto(pantryService.getPantryById(id));
    }
    @GetMapping("/user/{id}")
    public PantryDto getPantriesByUser(@PathVariable long id) {
        Pantry pantry = pantryService.getPantriesByUserId(id);
        PantryDto dto = new PantryDto();
        dto.setId(pantry.getId());
        dto.setItems(convertItemsToDto(pantry.getItems()));
        dto.setUserId(pantry.getUser().getId());
        return dto;
    }

        private List<ItemDto> convertItemsToDto(List<Item> items) {
            return items.stream()
                    .map(item -> {
                        ItemDto itemDto = new ItemDto();
                        itemDto.setId(item.getId());
                        itemDto.setName(item.getName());
                        itemDto.setQuantity(item.getQuantity());
                        itemDto.setExpirationDate(item.getExpirationDate());
                        itemDto.setGtin(String.valueOf(item.getGtin()));
                        itemDto.setBrand(item.getBrand());
                        itemDto.setImage(item.getImage());
                        itemDto.setCategory(item.getCategory());
                        return itemDto;
                    })
                    .collect(Collectors.toList());
        }

    @PostMapping("/create-pantry")
    public PantryDto createPantry(@RequestBody CreatePantryRequest pantry) {
        return pantryService.createPantry(pantry);
    }
    @GetMapping("/item/{id}")
    public ItemDto getItem(@PathVariable long id) {
        return itemService.getItemById(id);
    }

    @PostMapping("/create-item")
    public ItemDto createItem(@RequestBody CreateItemRequest itemRequest) {
        return itemService.createItem(itemRequest);
    }
}
