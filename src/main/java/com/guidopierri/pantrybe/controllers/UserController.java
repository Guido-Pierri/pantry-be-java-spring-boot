package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.responses.UserResponse;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/v1/users")

public class UserController {
    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("email/{email}")
    public ResponseEntity<UserResponse> getUserByEmail(@PathVariable String email) {

        return userService.getUserByEmail(email);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest user) {

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }
}
