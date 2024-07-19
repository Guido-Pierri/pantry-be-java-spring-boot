package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.dtos.requests.UpdateUserRequest;
import com.guidopierri.pantrybe.dtos.responses.DeleteUserResponse;
import com.guidopierri.pantrybe.exceptions.UserNotFoundException;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.permissions.Roles;
import com.guidopierri.pantrybe.repositories.ItemRepository;
import com.guidopierri.pantrybe.repositories.PantryRepository;
import com.guidopierri.pantrybe.repositories.UserRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;
    private final PantryRepository pantryRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ItemRepository itemRepository;
    Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, EntityMapper entityMapper, PantryRepository pantryRepository, BCryptPasswordEncoder passwordEncoder, ItemRepository itemRepository) {
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
        this.pantryRepository = pantryRepository;
        this.passwordEncoder = passwordEncoder;
        this.itemRepository = itemRepository;
    }

    @Cacheable(value = "users")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Cacheable(value = "users", key = "#id")
    public User getUserById(long id) {
        return (userRepository.findById(id).orElse(null));
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public User createUser(CreateUserRequest user) {
        logger.info("Creating user");
        Optional<User> userFromDatabase = userRepository.findUserByEmail(user.email());
        if (userFromDatabase.isEmpty()) {
            // Save the User first
            User newUser = saveUser(user);
            // Then create a new Pantry and associate it with the User
            Pantry pantry = new Pantry();
            pantry.setUser(newUser);
            logger.info("User pantry: {}", pantry);
            // Save the pantry and get the saved entity
            Pantry savedPantry = pantryRepository.save(pantry);
            logger.info("Pantry created successfully: {}", savedPantry);
            assert newUser != null;
            newUser.setPantry(savedPantry);
            logger.info("User created successfully: {}", newUser.getId());
            logger.info("User pantry: {}", newUser.getPantry());
            // Update the User with the associated Pantry
            User savedUser = userRepository.saveAndFlush(newUser);

            logger.info("Saved user with Id: {}", savedUser.getId());

            return savedUser;
        }
        return null;
    }

    private User saveUser(CreateUserRequest user) {

        if (user.id() == 0) {
            User newUser = new User();
            newUser.setFirstName((user.firstName()));
            newUser.setLastName(user.lastName());
            newUser.setEmail(user.email());
            if (user.authProvider().equals("google")) {
                newUser.setPassword(null);
            } else {
                newUser.setPassword(passwordEncoder.encode(user.password()));
            }
            newUser.setUsername(user.email());
            newUser.setImageUrl(user.imageUrl());
            newUser.setAccountNonExpired(true);
            newUser.setAccountNonLocked(true);
            newUser.setCredentialsNonExpired(true);
            newUser.setEnabled(true);
            newUser.setAuthProvider(user.authProvider());
            newUser.setRoles(Roles.USER);
            logger.info("User created successfully");
            return userRepository.save(newUser);

        }
        return null;
    }

    @Cacheable(value = "users", key = "#email")
    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    @Override
    @Cacheable(value = "users", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Transactional
    public ResponseEntity<DeleteUserResponse> deleteUser(User user) {
        logger.info("Deleting user");
        logger.info("User found with Id: {}", user.getId());
        if (user.getPantry() != null) {
            deleteItemsByPantryId(user.getPantry().getId());
            deletePantryByUser(user);
        }
        userRepository.delete(user);
        logger.info("User deleted successfully");
        return new ResponseEntity<>(new DeleteUserResponse("User deleted successfully"), HttpStatus.OK);
    }

    @Transactional
    public void deletePantryByUser(User user) {
        pantryRepository.deleteByUser(user);

    }

    @Transactional
    void deleteItemsByPantryId(Long pantryId) {
        itemRepository.deleteByPantryId(pantryId);
    }

    @CacheEvict(value = "users", allEntries = true)
    public ResponseEntity<DeleteUserResponse> deleteUserById(Long userId) {
        User user = findUserById(userId);
        return deleteUser(user);
    }

    public boolean checkEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @CacheEvict(value = "users", key = "#id")
    @Transactional
    public User updateUser(Long id, CreateUserRequest user) {
        logger.info("Updating user with id: {}", id);
        User userToUpdate = userRepository.findById(id).orElse(null);
        if (userToUpdate == null) {
            throw new UserNotFoundException(id);
        }

        userToUpdate.setFirstName(user.firstName());
        userToUpdate.setLastName(user.lastName());
        userToUpdate.setEmail(user.email());
        userToUpdate.setRoles(Roles.valueOf(user.roles()));
        userRepository.saveAndFlush(userToUpdate);
        logger.info("User with id {} updated successfully", id);
        return (userToUpdate);

    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public User updateUserProfile(Long id, UpdateUserRequest user) {
        User userToUpdate = userRepository.findById(id).orElse(null);

        if (userToUpdate == null) {
            return null;
        }

        userToUpdate.setFirstName(user.firstName());
        userToUpdate.setLastName(user.lastName());
        userToUpdate.setEmail(user.email());
        userRepository.save(userToUpdate);
        return userToUpdate;
    }
}

