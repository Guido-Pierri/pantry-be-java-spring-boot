package com.guidopierri.pantrybe.repositories;

import com.guidopierri.pantrybe.models.Item;
import org.slf4j.Logger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Logger logger = org.slf4j.LoggerFactory.getLogger(ItemRepository.class);

    @Override
    public List<Item> findAll();

    @Transactional
    void deleteByPantryId(Long pantryId);

}