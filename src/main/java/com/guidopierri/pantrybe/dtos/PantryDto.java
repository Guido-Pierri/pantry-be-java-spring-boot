package com.guidopierri.pantrybe.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PantryDto {
private long id;
private long userId;
private List<ItemDto> items = new ArrayList<>();

}
