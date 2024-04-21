package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.responses.UserResponse;
import com.guidopierri.pantrybe.dtos.requests.CreateUserRequest;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.repositories.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;
    UserService(UserRepository userRepository, EntityMapper entityMapper) {
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
    }
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUserById(long id) {
        return (userRepository.findById(id).orElse(null));
    }

    public UserDto createUser(CreateUserRequest user) {
        Optional<User> userFromDatabase = userRepository.findUserByEmail(user.email());
        if (userFromDatabase.isEmpty()){

        if (user.id() == 0) {
            User newUser = new User();
            newUser.setFirstName((user.firstName()));
            newUser.setLastName(user.lastName());
            newUser.setEmail(user.email());
            newUser.setPassword(user.password());
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


}
