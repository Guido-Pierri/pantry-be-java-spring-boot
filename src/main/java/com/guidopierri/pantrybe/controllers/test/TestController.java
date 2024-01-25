package com.guidopierri.pantrybe.controllers.test;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.config.PasswordConfig;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.repositories.UserRepository;
import com.guidopierri.pantrybe.services.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

@RestController

public class TestController {

    private final PasswordConfig passwordConfig;
    private final UserService userService;
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    public TestController(PasswordConfig passwordConfig, UserService userService, UserRepository userRepository, EntityMapper entityMapper
    ) {
        this.passwordConfig = passwordConfig;
        this.userService = userService;
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
    }
    private static final Logger logger = LoggerFactory.getLogger(TestController.class);


    /**
     * @author Josh Cummings
     */

        @GetMapping("/")
        public String index(@AuthenticationPrincipal Jwt jwt) {
            return String.format("Hello, %s!", jwt.getSubject());
        }

        @GetMapping("/message")
        public String message() {
            return "secret message";
        }
    @PostMapping("/register-test")
    public ResponseEntity<User> register(@Valid @RequestBody CreateUserRequest user, BindingResult result) {
        logger.info("Received request to register user: {}", user);

        if (result.hasErrors()) {
            logger.warn("Validation errors occurred: {}", result.getAllErrors());

            return ResponseEntity.badRequest().build();
        }
        User savedUser = userRepository.save(entityMapper.createUserRequestToUser(user));
        logger.info("User saved successfully: {}", savedUser);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);

    }
}
