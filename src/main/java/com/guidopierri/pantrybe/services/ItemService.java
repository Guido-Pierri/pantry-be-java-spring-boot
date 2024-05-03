package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.dtos.requests.CreateItemRequest;
import com.guidopierri.pantrybe.models.Item;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.repositories.ItemRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final PantryService pantryService;
    private final EntityMapper entityMapper;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(ItemService.class);

    ItemService(ItemRepository itemRepository, PantryService pantryService, EntityMapper entityMapper) {
        this.itemRepository = itemRepository;
        this.pantryService = pantryService;
        this.entityMapper = entityMapper;
    }

    public List<ItemDto> getItems() {
        return itemRepository.findAll().stream().map(entityMapper::itemToItemDto).toList();
    }


    public ItemDto getItemById(long id) {
        Item item = itemRepository.findById(id).orElse(null);
        return entityMapper.itemToItemDto(item);
    }

    public ItemDto createItem(CreateItemRequest itemRequest) {
        logger.info("itemRequest:" + itemRequest);
        if (itemRequest.id() == 0) {
            //find pantry by id
            if (itemRequest.pantryId().equalsIgnoreCase("0")) {
                return null;
            } else {

                //create item
                ItemDto itemDto = entityMapper.itemRequestToItemDto(itemRequest);
                logger.info("pantryId in itemRequest: {}", itemRequest.pantryId());
                logger.info("itemDto pantryId: {}", itemDto.pantryId());
                Item entity = entityMapper.itemDtoToItem(itemDto);
                logger.info("item entity: {}", entity);
                Pantry pantry = pantryService.getPantryById(Long.parseLong(itemRequest.pantryId()));
                entity.setPantry(pantry);
                Item item = itemRepository.save(entity);
                logger.info("pantry: {}", pantry);
                logger.info("item's pantry: {}", item.getPantry());
                ItemDto returnItem = entityMapper.itemToItemDto(item);
                logger.info("returnItem: {}", returnItem);
                return returnItem;
            }
        }
        return null;
    }

    //FIXME
    public List<ItemDto> getItemsByExpiration() {
        return null;
    }

    @Transactional
    @CacheEvict(value = "items", allEntries = true)
    public boolean deleteItem(long id) {

        itemRepository.deleteById(id);
        logger.info("Deleted item with id {}", id);
        return true;


    }
}


