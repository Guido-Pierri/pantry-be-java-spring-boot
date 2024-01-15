package com.guidopierri.pantrybe.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemRequest {
    public long id;
    public String name;
    public String quantity;
    public String expirationDate;
    public String gtin;
    public String brand;
    public String image;
    public String category;
    public String pantryId;
}
