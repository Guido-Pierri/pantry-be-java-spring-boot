package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.requests.CreatePantryRequest;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.permissions.Roles;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
class PantryServiceIT {
    private static final Logger log = LoggerFactory.getLogger(PantryServiceIT.class);
    @Autowired
    PantryService pantryService;
    @Autowired
    private UserService userService;

    @Test
    @DisplayName("Get pantry by user id")
    void getPantryByUserId() {
        // Arrange
        long id = 1L;
        CreateUserRequest createUserRequest = new CreateUserRequest(0,
                "test",
                "test",
                "test",
                "test",
                "test",
                Roles.USER.name(),
                "test");
        User user = userService.createUser(createUserRequest);
        PantryDto newPantry = pantryService.createPantry(new CreatePantryRequest(0, user.getId()));
        // Act
        System.out.println("All pantries: " + pantryService.getAllPantries());
        Pantry pantry = pantryService.getPantriesByUserId(user.getId());
        log.info("Pantry: " + pantry);
        // Assert
        assertNotNull(pantry, "Pantry should not be null");

        assertEquals(pantry.getUser().getId(), user.getId());
    }


}
