package com.guidopierri.pantrybe.dtos.responses;

public record ItemResponse(String name,
                           String gtin,
                           String brand,
                           String image,
                           String category) {

}
