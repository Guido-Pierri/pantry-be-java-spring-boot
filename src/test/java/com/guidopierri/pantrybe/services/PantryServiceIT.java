package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.requests.CreatePantryRequest;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.models.Pantry;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class PantryServiceIT {
    @Autowired
    PantryService pantryService;
    @Autowired
    private UserService userService;

    @Test
    void getPantryById() {
        // Arrange
        long id = 1L;
        CreateUserRequest createUserRequest = new CreateUserRequest(0,
                "test",
                "test",
                "test",
                "test",
                "test",
                "test",
                "test");
        UserDto userDto = userService.createUser(createUserRequest);
        PantryDto newPantry = pantryService.createPantry(new CreatePantryRequest(0, userDto.id()));
        // Act
        System.out.println("All pantries: " + pantryService.getAllPantries());
        Pantry pantry = pantryService.getPantryById(userDto.id());
        // Assert
        assertNotNull(pantry, "Pantry should not be null");

        assertEquals(pantry.getUser().getId(), id);
    }


}
