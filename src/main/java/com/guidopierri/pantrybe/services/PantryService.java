package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.requests.CreatePantryRequest;
import com.guidopierri.pantrybe.models.Item;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.repositories.PantryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PantryService {
    private final PantryRepository pantryRepository;
    private final com.guidopierri.pantrybe.services.UserService userService;
    private final EntityMapper entityMapper;
    PantryService(PantryRepository pantryRepository, UserService userService, EntityMapper entityMapper) {
        this.pantryRepository = pantryRepository;
        this.userService = userService;
        this.entityMapper = entityMapper;
    }
    public List<Pantry> getAllPantries() {
        return pantryRepository.findAll();
    }

    public PantryDto createPantry(CreatePantryRequest request) {
        if (request.getId() == 0) {
            Pantry pantry = new Pantry();

            // Fetch the user based on the user ID in the request
            User user = userService.getUserById(request.getUserId());
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

    public void addItemToPantry(long pantryId, Item item) {
        //FIXME:
        Pantry pantry = getPantryById(pantryId);
        pantry.addItem(item);
        System.out.println("pantry" + pantry);
        Pantry savedPantry = pantryRepository.save(pantry);
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

}
