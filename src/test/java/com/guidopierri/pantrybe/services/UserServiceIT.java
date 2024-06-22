package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.dtos.requests.UpdateUserRequest;
import com.guidopierri.pantrybe.exceptions.UserNotFoundException;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.permissions.Roles;
import com.guidopierri.pantrybe.repositories.ItemRepository;
import com.guidopierri.pantrybe.repositories.PantryRepository;
import com.guidopierri.pantrybe.repositories.UserRepository;
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
    UserDto userDto = new UserDto(0, "test", "test", "testuser@gmail.com", "test", Roles.USER.name());
    CreateUserRequest createUserRequest = new CreateUserRequest(0, "test", "test", "testuser@gmail.com", "test", "test", Roles.USER.name(), "google");
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
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);


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
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
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
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        userRepository.saveAndFlush(user);

        User user = userService.createUser(createUserRequest);
        assertThat(user).isNull();
    }

    @Test
    @DisplayName("Create user when user auth provider is google should store null password")
    void testCreateUserWhenUserAuthProviderIsGoogle() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        User user = userService.createUser(createUserRequest);
        assertNotNull(user);
        assertNull(user.getPassword());
    }

    @Test
    @DisplayName("Create user when user auth provider is credentials should store hashed password")
    void testCreateUserWhenUserAuthProviderIsCredentials() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        CreateUserRequest createUserRequestWithCredentials = new CreateUserRequest(0, "test", "test", "testuser@gmail.com", "test", "test", Roles.USER.name(), "credentials");
        User user = userService.createUser(createUserRequestWithCredentials);
        log.info("User: " + user);
        assertNotNull(user);
        assertNotNull(user.getPassword());
    }

    @Test
    @DisplayName("Get user by email")
    void testGetUserByEmail() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
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
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        assertNull(userService.getUserByEmail("xxx@xxx.xxx"));

    }

    @Test
    @DisplayName("Load user by username")
    void testLoadUserByUsername() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
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
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("xxx@xxx.xxx"));
    }

    @Test
    @DisplayName("Check email")
    void testCheckEmail() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        userRepository.saveAndFlush(user);
        assertTrue(userService.checkEmail(user.getEmail()));
    }

    @Test
    @DisplayName("Check email when email is not found should return false")
    void testCheckEmailWhenEmailNotFound() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        assertFalse(userService.checkEmail("xxx@xxx.xxx"));
    }

    @Test
    @DisplayName("Update user")
    void testUpdateUser() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        User newUser = userService.createUser(createUserRequest);
        log.info("User: " + newUser);
        User userUpdated = userService.updateUser(newUser.getId(), createUserRequest);
        assertNotNull(userUpdated);
        assertEquals(userUpdated.getFirstName(), newUser.getFirstName());
        assertEquals(userUpdated.getLastName(), newUser.getLastName());
        assertEquals(userUpdated.getEmail(), newUser.getEmail());
        assertEquals(userUpdated.getRoles().name(), newUser.getRoles().name());
        assertEquals(userUpdated.getPantry(), newUser.getPantry());

    }

    @Test
    @DisplayName("Update user when user is null should return UserNotFoundException")
    void testUpdateUserWhenUserIsNull() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        assertThrows(UserNotFoundException.class, () -> userService.updateUser(666L, createUserRequest));
    }

    @Test
    @DisplayName("Update user profile")
    void testUpdateUserProfile() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        User newUser = userService.createUser(createUserRequest);
        CreateUserRequest userRequest = createUserRequest;
        UpdateUserRequest createUserRequest = new UpdateUserRequest(0, userRequest.firstName(), userRequest.lastName(), userRequest.email(), userRequest.roles(), userRequest.authProvider());
        log.info("User: " + newUser);
        User userUpdated = userService.updateUserProfile(newUser.getId(), createUserRequest);
        assertNotNull(userUpdated);
        assertEquals(userUpdated.getFirstName(), newUser.getFirstName());
        assertEquals(userUpdated.getLastName(), newUser.getLastName());
        assertEquals(userUpdated.getEmail(), newUser.getEmail());
        assertEquals(userUpdated.getRoles().name(), newUser.getRoles().name());
        assertEquals(userUpdated.getPantry(), newUser.getPantry());

    }

    @Test
    @DisplayName("Update user profile when user is null should return null")
    void testUpdateUserProfileWhenUserIsNull() {
        UserService userService = new UserService(userRepository, entityMapper, pantryRepository, passwordEncoder, itemRepository);
        assertNull(userService.updateUserProfile(666L, new UpdateUserRequest(0, "test", "test", "test", "test", "test")));
    }
}


