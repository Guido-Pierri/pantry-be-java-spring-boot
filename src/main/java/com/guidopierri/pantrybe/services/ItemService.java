package com.guidopierri.pantrybe.services;

import com.guidopierri.pantrybe.config.EntityMapper;
import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.dtos.requests.CreateItemRequest;
import com.guidopierri.pantrybe.models.Item;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.repositories.ItemRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final com.guidopierri.pantrybe.services.PantryService pantryService;
    private final EntityMapper entityMapper;
    ItemService(ItemRepository itemRepository, PantryService pantryService , EntityMapper entityMapper ) {
        this.itemRepository = itemRepository;
        this.pantryService = pantryService;
        this.entityMapper = entityMapper;
    }
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

    public ItemDto createItem(CreateItemRequest itemRequest) {
        System.out.println("itemRequest:" +itemRequest);
    if (itemRequest.getId() == 0){
    //find pantry by id
        if (itemRequest.getPantryId().equalsIgnoreCase("0")){
        return null;
        } else {

        //create item
        ItemDto itemDto = entityMapper.itemRequestToItemDto(itemRequest);
            System.out.println("pantryId in itemRequest:" +itemRequest.getPantryId());
            System.out.println("itemDto pantryId:" +itemDto.pantryId);
        Item item = itemRepository.save(entityMapper.itemDtoToItem(itemDto));
        Pantry pantry = pantryService.getPantryById(Long.parseLong(itemRequest.pantryId));
            System.out.println("pantry:" +pantry);
            item.setPantry(pantry);
            System.out.println("item's pantry:" +item.getPantry());
        //save item to pantry
       pantryService.addItemToPantry(Long.parseLong(itemRequest.pantryId), item);
        //return item
            ItemDto returnItem = entityMapper.itemToItemDto(item);
            returnItem.setPantryId(item.getPantry().getId());
            System.out.println("returnItem:" +returnItem);
        return returnItem;
        }
    }
return null;
    }

    public ItemDto getItemById(long id) {
        Item item = itemRepository.findById((int) id).orElse(null);
        ItemDto itemDto = entityMapper.itemToItemDto(item);
        assert item != null;
        itemDto.setPantryId(item.getPantry().getId());
        return itemDto;
    }
}
