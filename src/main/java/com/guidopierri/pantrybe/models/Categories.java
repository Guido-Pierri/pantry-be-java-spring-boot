package com.guidopierri.pantrybe.models;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Categories {
    /**
     * TODO: ADD FRUIT(List.of(BANANA, KIWI, ORANGE, APPLE)),
     *  VEGETABLE(List.of(CARROT, LETTUCE, TOMATO, ONION)),
     *  FISH(List.of(SALMON, TUNA, COD, HAKE)),
     *  MEAT(List.of(BEEF, PORK, LAMB, CHICKEN)),
     *  POULTRY(List.of(TURKEY, DUCK, GOOSE, QUAIL)),
     *  DAIRY(List.of(MILK, CHEESE, YOGURT)),
     *  BAKERY(List.of(BREAD, PASTA, PIZZA)),
     *  SWEETS(List.of(CANDY, CHOCOLATE, COOKIES, CAKE)),
     *  OTHER(List.of(OTHER));
     */
    FRUIT,
    VEGETABLE,
    FISH,
    MEAT,
    POULTRY,
    DAIRY,
    BAKERY,
    SWEETS,
    OTHER;

    public static List<String> getCategories() {
    return Arrays.stream(Categories.values())
                 .map(Enum::name)
                 .collect(Collectors.toList());
}
}
