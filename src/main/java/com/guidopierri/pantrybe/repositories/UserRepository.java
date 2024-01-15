package com.guidopierri.pantrybe.repositories;

import com.guidopierri.pantrybe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.email = ?1 ")
    Optional<User> findUserByEmail(String email);
}
