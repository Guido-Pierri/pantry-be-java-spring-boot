package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class UserServiceTest {
    @Autowired
    UserService userService;

    @Test
    void createUser() {
       UserDto user = userService.createUser(new CreateUserRequest(0L, "Guido", "Pierri", "email@email.com","password"));
        assertNotNull(user);
        assertEquals("Guido", user.firstName());
        assertEquals("Pierri", user.lastName());
        assertEquals("email@email.com", user.email());
    }
    @Test
    void createUserNUll() {
        assertThrows(NullPointerException.class,()->userService.createUser(null));
    }

}
