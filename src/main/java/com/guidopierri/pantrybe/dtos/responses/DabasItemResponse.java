package com.guidopierri.pantrybe.dtos.responses;

public record DabasItemResponse(String gtin,
                                String name,
                                String brand,
                                String image,
                                String category,
                                String size,
                                String ingredients,
                                String productClassifications,
                                String bruteWeight,
                                String drainedWeight,
                                String level) {
}
