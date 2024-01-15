package com.guidopierri.pantrybe.config;

import com.guidopierri.pantrybe.models.Item;
import com.guidopierri.pantrybe.models.Pantry;
import com.guidopierri.pantrybe.models.User;
import com.guidopierri.pantrybe.dtos.ItemDto;
import com.guidopierri.pantrybe.dtos.PantryDto;
import com.guidopierri.pantrybe.dtos.UserDto;
import com.guidopierri.pantrybe.dtos.requests.CreateItemRequest;
import com.guidopierri.pantrybe.dtos.requests.CreatePantryRequest;
import com.guidopierri.pantrybe.dtos.responses.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EntityMapper {
ItemDto itemToItemDto(Item item);
Item itemDtoToItem(ItemDto itemDto);
ItemDto itemRequestToItemDto(CreateItemRequest itemRequest);
PantryDto pantryToPantryDto(Pantry pantry);
UserDto userToUserDto(User user);
UserResponse userToUserResponse(User user);
Pantry pantryRequestToPantry(CreatePantryRequest request);
}
