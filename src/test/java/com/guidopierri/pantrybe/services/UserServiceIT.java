package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.permissions.Roles;
import com.guidopierri.pantrybe.repositories.ItemRepository;
import com.guidopierri.pantrybe.repositories.PantryRepository;
import com.guidopierri.pantrybe.repositories.UserRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
public class UserServiceIT {

    private static final Logger log = LoggerFactory.getLogger(UserServiceIT.class);
    @Autowired
    ItemRepository itemRepository;
    User user = new User();
    UserDto userDto = new UserDto(0, "test", "test", "test", "test");
    CreateUserRequest createUserRequest = new CreateUserRequest(0, "test", "test", "testuser@gmail.com", "test", "test", Roles.USER.name(), "google");
    @Autowired
    EntityManager entityManager;
    @MockBean
    private EntityMapper entityMapper;
    @Autowired
    private UserRepository userRepository;
    @MockBean
    private PantryRepository pantryRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        pantryRepository.deleteAll();
        itemRepository.deleteAll();

        user.setFirstName("test");
        user.setLastName("user");
        user.setUsername("testuser");
        user.setEmail("testuser@gmail.com");
        user.setPassword("password");
        user.setImageUrl("imageUrl");
        user.setAuthProvider("google");
        user.setRoles(Roles.USER);

    }

    @Test
    @DisplayName("Get User By Id")
    void testGetUserById() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository, entityManager);


        userRepository.saveAndFlush(user);
        //entityManager.persistAndFlush(user);

        User foundUser = userService.getUserById(user.getId());
        log.info("Found user: " + foundUser);

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }

    @Test
    @DisplayName("Create User")
    void testCreateUser() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository, entityManager);
        log.info("users: " + userService.getUsers());
        User user = userService.createUser(createUserRequest);
        log.info("UserDto: " + userDto);
        assertNotNull(user);
        assertEquals(userDto.firstName(), user.getFirstName());
        assertEquals(userDto.lastName(), user.getLastName());
        assertEquals(userDto.email(), user.getEmail());
        assertEquals(userDto.imageUrl(), user.getImageUrl());

    }

    @Test
    @DisplayName("Create user when user exists should return null")
    void testCreateUserWhenUserExists() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository, entityManager);
        userRepository.saveAndFlush(user);

        User user = userService.createUser(createUserRequest);
        assertThat(user).isNull();
    }

    @Test
    @DisplayName("Create user when user auth provider is google should store null password")
    void testCreateUserWhenUserAuthProviderIsGoogle() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository, entityManager);
        User user = userService.createUser(createUserRequest);
        assertNotNull(user);
        assertNull(user.getPassword());
    }

    @Test
    @DisplayName("Create user when user auth provider is credentials should store hashed password")
    void testCreateUserWhenUserAuthProviderIsCredentials() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository, entityManager);
        CreateUserRequest createUserRequestWithCredentials = new CreateUserRequest(0, "test", "test", "testuser@gmail.com", "test", "test", Roles.USER.name(), "credentials");
        User user = userService.createUser(createUserRequestWithCredentials);
        log.info("User: " + user);
        assertNotNull(user);
        assertNotNull(user.getPassword());
    }

    @Test
    @DisplayName("Get user by email")
    void testGetUserByEmail() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository, entityManager);
        userRepository.saveAndFlush(user);
        User result = userService.getUserByEmail(user.getEmail());
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getImageUrl(), result.getImageUrl());
        assertEquals(user.getAuthProvider(), result.getAuthProvider());
        assertEquals(user.getRoles(), result.getRoles());
    }

    @Test
    @DisplayName("Get user by email when email is not found should return null")
    void testGetUserByEmailWhenEmailNotFound() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository, entityManager);
        assertNull(userService.getUserByEmail("xxx@xxx.xxx"));

    }

    @Test
    @DisplayName("Load user by username")
    void testLoadUserByUsername() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository, entityManager);
        userRepository.saveAndFlush(user);
        User result = (User) userService.loadUserByUsername(user.getUsername());
        assertNotNull(result);
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getImageUrl(), result.getImageUrl());
        assertEquals(user.getAuthProvider(), result.getAuthProvider());
        assertEquals(user.getRoles(), result.getRoles());
    }

    @Test
    @DisplayName("Load user by username when username is not found should throw exception")
    void testLoadUserByUsernameWhenUsernameNotFound() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository, entityManager);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("xxx@xxx.xxx"));
    }

    @Test
    @DisplayName("Delete user by id")
    void testDeleteUser() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository, entityManager);
        User user = userService.createUser(createUserRequest);
        log.info("User: " + user);
        log.info("User pantry: " + user.getPantry());
        userService.deleteUser(user.getId());
        assertNull(userService.getUserById(user.getId()));
    }
}

