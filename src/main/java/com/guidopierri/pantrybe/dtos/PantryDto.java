package com.guidopierri.pantrybe.dtos;

import java.util.List;

public record PantryDto(long id,
                        long userId,
                        List<ItemDto> items) {

}
