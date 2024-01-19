package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.requests.CreateItemRequest;
import com.guidopierri.pantrybe.dtos.requests.CreatePantryRequest;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.repositories.ItemRepository;
import com.guidopierri.pantrybe.services.ItemService;
import com.guidopierri.pantrybe.services.PantryService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

        return new PantryDto(pantry.getId(), pantry.getUser().getId(),pantryService.convertItemsToDto(pantry.getItems()) );
    }



    @PostMapping("/create-pantry")
    public PantryDto createPantry(@RequestBody CreatePantryRequest pantry) {
        return pantryService.createPantry(pantry);
    }
    @GetMapping("/item")
    public List<ItemDto> getItems() {
        return itemService.getItems();
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
