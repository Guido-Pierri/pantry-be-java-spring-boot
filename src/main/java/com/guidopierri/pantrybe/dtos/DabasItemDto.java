package com.guidopierri.pantrybe.dtos;

public record DabasItemDto(long id,
                           String name,
                           long quantity,
                           String expirationDate,
                           String brand,
                           String image,
                           String category,
                           long pantryId) {
}
