package com.guidopierri.pantrybe.repositories;

import com.guidopierri.pantrybe.models.Pantry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PantryRepository extends JpaRepository<Pantry, Long> {
}
