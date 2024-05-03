package com.guidopierri.pantrybe.repositories;

import com.guidopierri.pantrybe.models.Item;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Logger logger = org.slf4j.LoggerFactory.getLogger(ItemRepository.class);

    @Override
    public List<Item> findAll();

    /*@Modifying
    @Query("DELETE FROM Item i WHERE i.id = ?1")*/
    //void deleteItemById(long id);
}