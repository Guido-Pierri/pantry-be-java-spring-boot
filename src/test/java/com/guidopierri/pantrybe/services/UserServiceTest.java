package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.permissions.Roles;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void createUser() {
        UserDto user = userService.createUser(new CreateUserRequest(0L, "Guido", "Pierri", "email@email.com", null, "password", Roles.USER.name(), "test"));
        assertNotNull(user);
        assertEquals("Guido", user.firstName());
        assertEquals("Pierri", user.lastName());
        assertEquals("email@email.com", user.email());
    }

    @Test
    void createUserNUll() {
        assertThrows(NullPointerException.class, () -> userService.createUser(null));
    }

}
