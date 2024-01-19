package com.guidopierri.pantrybe.dtos.requests;

import jakarta.validation.constraints.NotNull;

public record CreateItemRequest(long id,
                                @NotNull String name,
                                @NotNull String quantity,
                                @NotNull String expirationDate,
                                @NotNull String gtin,
                                @NotNull String brand,
                                @NotNull String image,
                                @NotNull String category,
                                @NotNull String pantryId) {

}
