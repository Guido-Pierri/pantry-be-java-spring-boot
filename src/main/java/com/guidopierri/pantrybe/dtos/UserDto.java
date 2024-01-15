package com.guidopierri.pantrybe.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    public long id;
    public String firstName;
    public String lastName;
    public String email;


}
