package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.dtos.requests.UpdateUserRequest;
import com.guidopierri.pantrybe.dtos.responses.DeleteUserResponse;
import com.guidopierri.pantrybe.dtos.responses.UserResponse;
import com.guidopierri.pantrybe.exceptions.UserNotFoundException;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.permissions.Roles;
import com.guidopierri.pantrybe.repositories.PantryRepository;
import com.guidopierri.pantrybe.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return (userRepository.findById(id).orElse(null));
    }

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
                    newUser.setPassword(passwordEncoder.encode(user.password()));
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

    public List<Pantry> convertToPantry(List<PantryDto> dtoList) {
        List<Pantry> pantryList = new ArrayList<>();

        for (PantryDto dto : dtoList) {

            Pantry pantry = new Pantry();
            pantry.setId(dto.id());
            pantry.setUser(userRepository.findById(dto.userId()).orElse(null));
            pantryList.add(pantry);
        }
        return pantryList;
    }

    public ResponseEntity<UserResponse> getUserByEmail(String email) {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(entityMapper.userToUserResponse(user.orElse(null)), HttpStatus.OK);


    }

    public User getByemail(String email) {
        return userRepository.findUserByEmail(email).orElse(null);
    }

    public User getUserByemailAndPassword(String email, String password) {
        return userRepository.findUserByEmailAndPassword(email, password).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // TODO - What if we do not FIND user?

        return userRepository.findByUsername(username);
    }

    @Transactional
    public ResponseEntity<DeleteUserResponse> deleteUser(Long userId) {
        // Find the user
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));

        // Delete the associated records in the pantry table
        pantryRepository.deleteByUser(user);

        // Delete the user
        userRepository.delete(user);
        return new ResponseEntity<>(new DeleteUserResponse("User deleted successfully"), HttpStatus.OK);
    }

    public boolean checkEmail(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

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

    public UserResponse updateUserProfile(Long id, UpdateUserRequest user) {
        User userToUpdate = userRepository.findById(id).orElse(null);

        if (userToUpdate != null) {
            userToUpdate.setFirstName(user.firstName());
            userToUpdate.setLastName(user.lastName());
            userToUpdate.setEmail(user.email());
            //userToUpdate.setImageUrl(user.imageUrl());
            //userToUpdate.setPassword(user.password());
            //userToUpdate.setRoles(Roles.valueOf(user.roles()));
            userRepository.save(userToUpdate);
            return entityMapper.userToUserResponse(userToUpdate);
        }
        return null;
    }
}

