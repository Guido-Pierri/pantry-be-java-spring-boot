package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.requests.CreatePantryRequest;
import com.guidopierri.pantrybe.models.Item;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.repositories.PantryRepository;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PantryService {
    private final PantryRepository pantryRepository;
    private final UserService userService;
    private final EntityMapper entityMapper;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(PantryService.class);

    PantryService(PantryRepository pantryRepository, UserService userService, EntityMapper entityMapper) {
        this.pantryRepository = pantryRepository;
        this.userService = userService;
        this.entityMapper = entityMapper;
    }

    public List<Pantry> getAllPantries() {
        return pantryRepository.findAll();
    }

    public PantryDto createPantry(CreatePantryRequest request) {
        if (request.id() == 0) {
            Pantry pantry = new Pantry();

            // Fetch the user based on the user ID in the request
            User user = userService.getUserById(request.userId());
            pantry.setUser(user);

            // Save the pantry and get the saved entity
            Pantry savedPantry = pantryRepository.save(pantry);

            // Map the saved pantry entity to a DTO

            return entityMapper.pantryToPantryDto(savedPantry);
        }

        return null;
    }


    public Pantry getPantryById(long pantryId) {
        return (pantryRepository.findById(pantryId).orElse(null));
    }


    public Pantry getPantriesByUserId(long id) {
        User user = userService.getUserById(id);
        System.out.println("user" + user);
        long pantryId = user.getPantry().getId();
        System.out.println("pantryId" + pantryId);
        return pantryRepository.findById(pantryId).orElse(null);
    }

    public List<ItemDto> convertItemsToDto(List<Item> items) {
        return items.stream()
                .map(item -> {
                    return new ItemDto(item.getId(),
                            item.getName(),
                            item.getQuantity(),
                            item.getExpirationDate(),
                            item.getBrand(),
                            item.getImage(),
                            item.getCategory(),
                            item.getPantry().getId());
                })
                .collect(Collectors.toList());
    }

    public void addItemToPantry(long pantryId, Item item) {
        //FIXME:
        Pantry pantry = getPantryById(pantryId);
        pantry.addItem(item);
        logger.info("pantry: {}", pantry);
        pantryRepository.save(pantry);
    }

    public List<String> getPantryCategories() {
        return pantryRepository.findAll().stream()
                .map(Pantry::getItems)
                .flatMap(List::stream)
                .map(Item::getCategory)
                .distinct()
                .collect(Collectors.toList());

    }

    public List<String> getPantryItemsNames(String id) {
        User user = userService.getUserById(Long.parseLong(id));
        return pantryRepository.findById(user.getPantry().getId()).stream()
                .map(Pantry::getItems)
                .flatMap(List::stream)
                .map(Item::getName)
                .distinct()
                .collect(Collectors.toList());

    }
}
