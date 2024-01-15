package com.guidopierri.pantrybe.dtos;

import lombok.Data;

@Data
public class ItemDto {
    public long id;
    public String name;
    public long quantity;
    public String expirationDate;
    public String gtin;
    public String brand;
    public String image;
    public String category;
    public long pantryId;
}
