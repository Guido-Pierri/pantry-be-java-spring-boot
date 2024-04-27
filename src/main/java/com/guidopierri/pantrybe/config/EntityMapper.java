package com.guidopierri.pantrybe.config;

import com.guidopierri.pantrybe.dtos.DabasItemDto;
import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.requests.CreateDabasItemRequest;
import com.guidopierri.pantrybe.dtos.requests.CreateItemRequest;
import com.guidopierri.pantrybe.dtos.requests.CreatePantryRequest;
import com.guidopierri.pantrybe.dtos.responses.DabasItemResponse;
import com.guidopierri.pantrybe.dtos.responses.UserResponse;
import com.guidopierri.pantrybe.models.DabasItem;
import com.guidopierri.pantrybe.models.Item;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EntityMapper {
    default ItemDto itemToItemDto(Item item) {
        if (item == null) {
            return null;
        }

        long id = 0L;
        String name = null;
        long quantity = 0L;
        String expirationDate = null;
        String gtin = null;
        String brand = null;
        String image = null;
        String category = null;

        id = item.getId();
        name = item.getName();
        quantity = item.getQuantity();
        expirationDate = item.getExpirationDate();
        brand = item.getBrand();
        image = item.getImage();
        category = item.getCategory();

        long pantryId = 0L;
        pantryId = item.getPantry().getId();

        return new ItemDto(id, name, quantity, expirationDate, brand, image, category, pantryId);

    }

    ;

    Item itemDtoToItem(ItemDto itemDto);

    ItemDto itemRequestToItemDto(CreateItemRequest itemRequest);

    DabasItem customItemRequestToCustomItem(CreateDabasItemRequest itemRequest);

    PantryDto pantryToPantryDto(Pantry pantry);

    UserDto userToUserDto(User user);

    UserResponse userToUserResponse(User user);

    Pantry pantryRequestToPantry(CreatePantryRequest request);

    DabasItemResponse customItemToCustomItemResponse(DabasItem item);

    List<DabasItemDto> customItemToCustomItemDto(List<DabasItem> customItems);
}
