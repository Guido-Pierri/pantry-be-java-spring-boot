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
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary = "Get all pantries")
    @GetMapping("")
    public List<Pantry> getPantries() {
        return pantryService.getAllPantries();
    }

    @Operation(summary = "Get pantry by id")
    @GetMapping("/{id}")
    public PantryDto getPantry(@PathVariable long id) {
        return entityMapper.pantryToPantryDto(pantryService.getPantryById(id));
    }

    @Operation(summary = "Get pantry by user id")
    @GetMapping("/user/{id}")
    public PantryDto getPantriesByUser(@PathVariable long id) {
        Pantry pantry = pantryService.getPantriesByUserId(id);

        return new PantryDto(pantry.getId(), pantry.getUser().getId(), pantryService.convertItemsToDto(pantry.getItems()));
    }

    @Operation(summary = "Create a new pantry")
    @PostMapping("/create-pantry")
    public PantryDto createPantry(@RequestBody CreatePantryRequest pantry) {
        return pantryService.createPantry(pantry);
    }

    @Operation(summary = "Get all items")
    @GetMapping("/items")
    public List<ItemDto> getItems() {
        return itemService.getItems();
    }

    @Operation(summary = "Get item by id")
    @GetMapping("/item/{id}")
    public ItemDto getItem(@PathVariable long id) {
        return itemService.getItemById(id);
    }

    @Operation(summary = "Create a new item in the pantry.")
    @PostMapping("/create-item")
    public ItemDto createItem(@RequestBody CreateItemRequest itemRequest) {
        return itemService.createItem(itemRequest);
    }

    @Operation(summary = "Get all items by category")
    @GetMapping("/categories")
    public List<String> getCategories() {
        return Categories.getCategories();
    }

    @Operation(summary = "Get all items that are about to expire.")
    @GetMapping("/expire-soon")
    public List<ItemDto> getItemsByExpiration() {
        return itemService.getItemsByExpiration();
    }

    @Operation(summary = "Delete an item by id")
    @DeleteMapping("/delete-item/{id}")
    public ResponseEntity<String> deleteItem(@PathVariable String id) {
        if (itemService.deleteItem(Long.parseLong(id))) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @Operation(summary = "Get all items categories")
    @GetMapping("/article-categories")
    public ResponseEntity<List<String>> getAllItemsCategories() {
        List<String> categories = pantryService.getPantryCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(summary = "Get a pantry's all items names by pantry id")
    @GetMapping("/article-names/{id}")
    public ResponseEntity<List<String>> getAllItemsNames(@PathVariable String id) {
        List<String> names = pantryService.getPantryItemsNames(id);
        return new ResponseEntity<>(names, HttpStatus.OK);
    }
}
