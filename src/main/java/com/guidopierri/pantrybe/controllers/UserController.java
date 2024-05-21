package com.guidopierri.pantrybe.controllers;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.dtos.requests.UpdateUserRequest;
import com.guidopierri.pantrybe.dtos.responses.DeleteUserResponse;
import com.guidopierri.pantrybe.dtos.responses.UserResponse;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.permissions.Roles;
import com.guidopierri.pantrybe.services.SpringSecurityUserDetailsService;
import com.guidopierri.pantrybe.services.UserService;
import com.guidopierri.pantrybe.utils.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService userService;
    private final SpringSecurityUserDetailsService userDetailsService;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EntityMapper entityMapper;
    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, SpringSecurityUserDetailsService userDetailsService, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil, EntityMapper entityMapper) {
        this.userService = userService;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.entityMapper = entityMapper;
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>((userService.getUsers()), HttpStatus.OK);
    }

    @GetMapping("email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>().put("message", "User not found"));
        }
        User user = userService.getUserByemailAndPassword(email, userDetails.getPassword());
        final String jwt = jwtUtil.generateToken(userDetails);

        return new ResponseEntity<>(new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getImageUrl(), user.getPassword(), user.getRoles().name(), jwt),
                HttpStatus.OK);
    }

    @PostMapping("login/{email}")
    public ResponseEntity<?> login(@PathVariable String email, @RequestBody String password) {
        if (password == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>().put("message", "Password is required"));
        }
        final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new HashMap<String, String>().put("message", "User not found"));
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new HashMap<String, String>().put("message", "Invalid password"));
        }
        User user = userService.getUserByemailAndPassword(email, userDetails.getPassword());

        final String jwt = jwtUtil.generateToken(userDetails);

        logger.info("Login successful");
        return new ResponseEntity<>(new UserResponse(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getImageUrl(), user.getPassword(), user.getRoles().name(), jwt),
                HttpStatus.OK);
    }

    @PostMapping("/check-email")
    public ResponseEntity<Map<String, Boolean>> checkEmail(@RequestBody String token) {
        Jwt jwt = jwtUtil.jwtDecoder().decode(token);
        String email = jwt.getClaimAsString("email");
        Map<String, Boolean> response = new HashMap<>();
        String s = "exists";
        if (email == null) {
            response.put(s, false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        boolean exists = userService.checkEmail(email);
        if (Boolean.TRUE.equals(exists)) {
            response.put(s, true);
        } else {
            response.put(s, false);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/fetch-logged-in-user/{email}")
    public ResponseEntity<UserResponse> getLoggedInUser(@PathVariable String email) {
        var userDetails = userService.getByemail(email);
        final String jwt = jwtUtil.generateToken(userDetails);

        UserResponse userResponse = new UserResponse(userDetails.getId(), userDetails.getFirstName(), userDetails.getLastName(), userDetails.getEmail(), userDetails.getImageUrl(), userDetails.getPassword(), userDetails.getRoles().name(), jwt);
        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        return new ResponseEntity<>(entityMapper.userToUserDto(userService.getUserById(id)), HttpStatus.OK);
    }

    @GetMapping("username/{username}")
    public ResponseEntity<UserDetails> getUserByUsername(@PathVariable String username) {

        return new ResponseEntity<UserDetails>(userDetailsService.loadUserByUsername(username), HttpStatus.FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody CreateUserRequest user) {

        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @GetMapping("/user")
    public ResponseEntity<UserDto> getUser(@RequestBody CreateUserRequest user) {

        return new ResponseEntity<>(entityMapper.userToUserDto(userService.getUserByemailAndPassword(user.email(), user.password())), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('admin:delete', 'user:delete')")
    public ResponseEntity<DeleteUserResponse> deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @GetMapping("/all-roles")
    public ResponseEntity<List<Roles>> getAllRoles() {
        List<Roles> roles = new ArrayList<>();
        Collections.addAll(roles, Roles.values());
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('admin:write', 'user:write')")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody CreateUserRequest user) {
        UserResponse response = userService.updateUser(id, user);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/update-user-profile/{id}")
    @PreAuthorize("hasAuthority('user:write')")
    public ResponseEntity<?> updateUserProfile(@PathVariable Long id, @RequestBody UpdateUserRequest user) {
        UserResponse response = userService.updateUserProfile(id, user);
        if (response == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/check-token")
    public ResponseEntity<?> checkToken() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
