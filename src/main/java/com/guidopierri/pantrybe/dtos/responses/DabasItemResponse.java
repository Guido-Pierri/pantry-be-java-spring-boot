package com.guidopierri.pantrybe.dtos.responses;

public record DabasItemResponse(String name,
                                String gtin,
                                String brand,
                                String image,
                                String category) {
}
