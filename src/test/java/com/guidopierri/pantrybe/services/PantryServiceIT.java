package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.permissions.Roles;
import com.guidopierri.pantrybe.repositories.PantryRepository;
import com.guidopierri.pantrybe.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class PantryServiceIT {
    private static final Logger log = LoggerFactory.getLogger(PantryServiceIT.class);
    @Autowired
    PantryService pantryService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PantryRepository pantryRepository;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        pantryRepository.deleteAll();
    }

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
                "test", false);
        User user = userService.createUser(createUserRequest);
        //pantryService.createPantry(new CreatePantryRequest(0, user.getId()));
        // Act
        log.info("All pantries: " + pantryService.getAllPantries());
        Pantry pantry = pantryService.getPantriesByUserId(user.getId());
        log.info("Pantry: " + pantry);
        // Assert
        assertNotNull(pantry, "Pantry should not be null");

        assertEquals(pantry.getId(), user.getPantry().getId());
        assertEquals(pantry.getUser().getId(), user.getId());
        assertEquals(pantry.getUser().getUsername(), user.getUsername());
        assertEquals(pantry.getUser().getEmail(), user.getEmail());
        assertEquals(pantry.getUser().getFirstName(), user.getFirstName());
        assertEquals(pantry.getUser().getLastName(), user.getLastName());
        assertEquals(pantry.getUser().getImageUrl(), user.getImageUrl());
        assertEquals(pantry.getUser().getAuthProvider(), user.getAuthProvider());
        assertEquals(pantry.getUser().getRoles(), user.getRoles());
        assertEquals(pantry.getUser().isFirstTimeUser(), user.isFirstTimeUser());

    }

    @Test
    @DisplayName("Get pantry by pantry is null should return null")
    void getPantryByUserIdPantryIsNull() {
        // Arrange
        long id = 1L;
        CreateUserRequest createUserRequest = new CreateUserRequest(0,
                "test",
                "test",
                "test",
                "test",
                "test",
                Roles.USER.name(),
                "test", false);
        User user = userService.createUser(createUserRequest);
        pantryRepository.deleteAll();
        // Act
        Pantry pantry = pantryService.getPantriesByUserId(user.getId());
        // Assert
        assertNull(pantry, "Pantry should be null");
    }

}
