package com.guidopierri.pantrybe.dtos.requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreatePantryRequest {
    public long id;
    public long userId;
}
