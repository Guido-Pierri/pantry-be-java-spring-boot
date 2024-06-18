package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.dtos.requests.UpdateUserRequest;
import com.guidopierri.pantrybe.dtos.responses.DeleteUserResponse;
import com.guidopierri.pantrybe.dtos.responses.UserResponse;
import com.guidopierri.pantrybe.exceptions.UserNotFoundException;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.permissions.Roles;
import com.guidopierri.pantrybe.repositories.ItemRepository;
import com.guidopierri.pantrybe.repositories.PantryRepository;
import com.guidopierri.pantrybe.repositories.UserRepository;
import jakarta.transaction.Transactional;
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

import java.util.List;
import java.util.Objects;
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
            // Save the pantry and get the saved entity
            Pantry savedPantry = pantryRepository.save(pantry);
            newUser.setPantry(savedPantry);
            logger.info("User created successfully: {}", newUser);
            // Update the User with the associated Pantry
            User savedUser = userRepository.save(newUser);
            logger.info("Saved user: {}", savedUser);

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
            logger.info("User auth provider: {}", user.authProvider());
            if (user.authProvider().equals("google")) {
                logger.info("Google user, setting password to null");
                newUser.setPassword(null);
            } else {
                logger.info("Not a google user, setting password");
                logger.info("Password user: {}", user.password());
                newUser.setPassword(passwordEncoder.encode(user.password()));
                logger.info("Password newUser: {}", newUser.getPassword());


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
    public ResponseEntity<UserResponse> getUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entityMapper.userToUserResponse(user.orElse(null)), HttpStatus.OK);


    }

    @Cacheable(value = "users", key = "#email")
    public User getByemail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    @Cacheable(value = "users", key = "#email")
    public User getUserByemailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password).orElse(null);
    }

    @Override
    @Cacheable(value = "users", key = "#username")
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }

    @Transactional
    @CacheEvict(value = "users", allEntries = true)
    public ResponseEntity<DeleteUserResponse> deleteUser(Long userId) {
        logger.info("Deleting user");
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        itemRepository.deleteByPantryId(user.getPantry().getId());
        pantryRepository.deleteByUser(user);
        userRepository.delete(user);
        logger.info("User deleted successfully");
        return new ResponseEntity<>(new DeleteUserResponse("User deleted successfully"), HttpStatus.OK);
    }

    public boolean checkEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @CacheEvict(value = "users", key = "#id")
    public UserResponse updateUser(Long id, CreateUserRequest user) {
        User userToUpdate = userRepository.findById(id).orElse(null);
        if (!Objects.equals(user.roles(), Roles.USER.toString())) {
            return null;
        }
        if (userToUpdate != null) {
            userToUpdate.setFirstName(user.firstName());
            userToUpdate.setLastName(user.lastName());
            userToUpdate.setEmail(user.email());
            userToUpdate.setImageUrl(user.imageUrl());
            //userToUpdate.setPassword(user.password());
            userToUpdate.setRoles(Roles.valueOf(user.roles()));
            userRepository.save(userToUpdate);
            return entityMapper.userToUserResponse(userToUpdate);
        }
        return null;
    }

    @CacheEvict(value = "users", allEntries = true)
    public UserResponse updateUserProfile(Long id, UpdateUserRequest user) {
        User userToUpdate = userRepository.findById(id).orElse(null);

        if (userToUpdate != null) {
            userToUpdate.setFirstName(user.firstName());
            userToUpdate.setLastName(user.lastName());
            userToUpdate.setEmail(user.email());
            userRepository.save(userToUpdate);
            return entityMapper.userToUserResponse(userToUpdate);
        }
        return null;
    }
}

