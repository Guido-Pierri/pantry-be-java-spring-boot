package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.requests.CreatePantryRequest;
import com.guidopierri.pantrybe.models.Item;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.repositories.PantryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
            PantryDto dto = entityMapper.pantryToPantryDto(savedPantry);

            return dto;
        }

        return null;
    }


    public Pantry getPantryById(long pantryId) {
        return (pantryRepository.findById(pantryId).orElse(null));
    }

    public void addItemToPantry(long pantryId, Item item) {
        Pantry pantry = getPantryById(pantryId);
        pantry.addItem(item);
        System.out.println("pantry" + pantry);
        Pantry savedPantry = pantryRepository.save(pantry);
    }

    public Pantry getPantriesByUser(long id) {
        User user = userService.getUserById(id);
        return pantryRepository.findById(user.getPantry().getId()).orElse(null);
    }
}
