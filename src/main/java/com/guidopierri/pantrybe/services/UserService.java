package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.dtos.requests.UpdateUserRequest;
import com.guidopierri.pantrybe.dtos.responses.DeleteUserResponse;
import com.guidopierri.pantrybe.dtos.responses.UserResponse;
import com.guidopierri.pantrybe.exceptions.UserNotFoundException;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.permissions.Roles;
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

    Logger logger = org.slf4j.LoggerFactory.getLogger(UserService.class);

    @Autowired
    public UserService(UserRepository userRepository, EntityMapper entityMapper, PantryRepository pantryRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
        this.pantryRepository = pantryRepository;
        this.passwordEncoder = passwordEncoder;
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
    public UserDto createUser(CreateUserRequest user) {
        logger.info("Creating user");
        logger.info(user.toString());
        Optional<User> userFromDatabase = userRepository.findUserByEmail(user.email());
        if (userFromDatabase.isEmpty()) {

            if (user.id() == 0) {
                User newUser = new User();
                newUser.setFirstName((user.firstName()));
                newUser.setLastName(user.lastName());
                newUser.setEmail(user.email());
                if (user.authProvider().equals("google")) {
                    newUser.setPassword(null);
                } else {
                    logger.info("Password before encoding: {}", user.password());
                    newUser.setPassword(user.password());
                    logger.info("Password after encoding: {}", newUser.getPassword());

                }
                newUser.setUsername(user.email());
                newUser.setImageUrl(user.imageUrl());
                newUser.setAccountNonExpired(true);
                newUser.setAccountNonLocked(true);
                newUser.setCredentialsNonExpired(true);
                newUser.setEnabled(true);
                newUser.setAuthProvider(user.authProvider());
                newUser.setRoles(Roles.valueOf(user.roles()));
                userRepository.save(newUser);
                return entityMapper.userToUserDto(newUser);
            }
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
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
        pantryRepository.deleteByUser(user);
        userRepository.delete(user);
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

