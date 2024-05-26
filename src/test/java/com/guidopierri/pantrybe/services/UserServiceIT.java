package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.permissions.Roles;
import com.guidopierri.pantrybe.repositories.ItemRepository;
import com.guidopierri.pantrybe.repositories.PantryRepository;
import com.guidopierri.pantrybe.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;


@ActiveProfiles("test")
@DataJpaTest
public class UserServiceIT {

    @Autowired
    private TestEntityManager entityManager;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PantryRepository pantryRepository;

    @MockBean
    private BCryptPasswordEncoder passwordEncoder;
    @MockBean
    ItemRepository itemRepository;

    @Test
    void testGetUserById() {
        User user = new User();
        user.setFirstName("test");
        user.setLastName("user");
        user.setEmail("testuser@gmail.com");
        user.setPassword("password");
        user.setImageUrl("imageUrl");
        user.setAuthProvider("google");
        user.setRoles(Roles.USER);
        entityManager.persistAndFlush(user);

        UserService userService = new UserService(userRepository, null, pantryRepository, passwordEncoder, itemRepository);
        User foundUser = userService.getUserById(user.getId());

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo(user.getEmail());
    }
}

