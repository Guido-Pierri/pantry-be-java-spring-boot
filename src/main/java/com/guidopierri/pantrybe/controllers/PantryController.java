package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.requests.CreateItemRequest;
import com.guidopierri.pantrybe.dtos.requests.CreatePantryRequest;
import com.guidopierri.pantrybe.models.Categories;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.services.ItemService;
import com.guidopierri.pantrybe.services.PantryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

        return new PantryDto(pantry.getId(), pantry.getUser().getId(), pantryService.convertItemsToDto(pantry.getItems()));
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

    @GetMapping("/categories")
    public List<String> getCategories() {
        return Categories.getCategories();
    }

    @GetMapping("/expire-soon")
    public List<ItemDto> getItemsByExpiration() {
        return itemService.getItemsByExpiration();
    }

    @DeleteMapping("/delete-item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable String id) {
        if (itemService.deleteItem(Long.parseLong(id))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/article-categories")
    public ResponseEntity<List<String>> getAllItemsCategories() {
        List<String> categories = pantryService.getPantriesByItemName();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }
}
