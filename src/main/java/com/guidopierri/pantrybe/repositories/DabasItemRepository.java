package com.guidopierri.pantrybe.repositories;

import com.guidopierri.pantrybe.models.DabasItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DabasItemRepository extends JpaRepository<DabasItem, Long> {
    @Query("SELECT d FROM DabasItem d WHERE d.gtin = ?1")
    Optional<DabasItem> findDabasItemByGtin(String gtin);


}
