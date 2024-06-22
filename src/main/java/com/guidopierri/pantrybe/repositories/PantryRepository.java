package com.guidopierri.pantrybe.repositories;

import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PantryRepository extends JpaRepository<Pantry, Long> {
    //@Query("SELECT p FROM Pantry p WHERE p.user = ?1 ")
    @Transactional
    void deleteByUser(User user);

}
