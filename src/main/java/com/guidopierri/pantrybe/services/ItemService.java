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

    /**
     * Creates a new item in the pantry.
     * <p>
     * This method is used to create a new item in the pantry. It takes a CreateItemRequest object as input,
     * which contains the details of the item to be created. The method is transactional, which means that
     * all database operations within the method are part of a single transaction.
     * <p>
     * The method first checks if the id of the item in the request is 0, which indicates a new item. If the id is not 0,
     * the method returns null. If the id is 0, the method then checks if the pantryId in the request is "0". If the pantryId is "0",
     * the method returns null. If the pantryId is not "0", the method proceeds to create the item.
     * <p>
     * The method uses an EntityMapper to convert the CreateItemRequest object to an ItemDto object, and then to an Item entity.
     * It then retrieves the Pantry entity associated with the pantryId from the request, and sets this Pantry entity on the Item entity.
     * The Item entity is then saved to the database using the ItemRepository.
     * <p>
     * After saving the Item entity, the method converts it back to an ItemDto object using the EntityMapper, and returns this object.
     * <p>
     * The method also uses the @CacheEvict annotation to clear the "items" cache after the item is created, ensuring that future requests
     * for items will retrieve the latest data from the database.
     *
     * @param itemRequest a CreateItemRequest object containing the details of the item to be created
     * @return an ItemDto object representing the created item, or null if the item could not be created
     */
    @Transactional
    @CacheEvict(value = "items", allEntries = true)
    public ItemDto createItem(CreateItemRequest itemRequest) {
        logger.info("Creating item: {}", itemRequest);
        if (itemRequest.id() == 0) {
            if (itemRequest.pantryId().equalsIgnoreCase("0")) {
                return null;
            } else {

                //create item
                ItemDto itemDto = entityMapper.itemRequestToItemDto(itemRequest);
                Item entity = entityMapper.itemDtoToItem(itemDto);
                Pantry pantry = pantryService.getPantryById(Long.parseLong(itemRequest.pantryId()));
                entity.setPantry(pantry);
                Item item = itemRepository.save(entity);
                ItemDto returnItem = entityMapper.itemToItemDto(item);
                logger.info("Item created: {}", returnItem);
                return returnItem;
            }
        }
        return null;
    }

    //FIXME
    public List<ItemDto> getItemsByExpiration() {
        return null;
    }

    /**
     * Deletes an item from the pantry.
     * <p>
     * This method is used to delete an item from the pantry. It takes an item id as input,
     * which is the id of the item to be deleted. The method is transactional, which means that
     * the delete operation is part of a single transaction.
     * <p>
     * The method uses the ItemRepository to delete the item with the given id from the database.
     * After the item is deleted, a log message is printed indicating the id of the deleted item.
     * <p>
     * The method also uses the @CacheEvict annotation to clear the "items" cache after the item is deleted,
     * ensuring that future requests for items will retrieve the latest data from the database.
     *
     * @param id the id of the item to be deleted
     * @return a boolean value indicating that the item has been deleted. Always returns true.
     */
    @Transactional
    @CacheEvict(value = "items", allEntries = true)
    public boolean deleteItem(long id) {

        itemRepository.deleteById(id);
        logger.info("Deleted item with id {}", id);
        return true;


    }
}
