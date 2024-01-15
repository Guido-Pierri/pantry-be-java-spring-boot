package com.guidopierri.pantrybe.repositories;

import com.guidopierri.pantrybe.models.Item;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<Item, Integer> {
    @Override
    public List<Item> findAll();
}
