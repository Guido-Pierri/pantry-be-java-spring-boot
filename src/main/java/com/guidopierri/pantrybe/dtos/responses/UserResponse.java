package com.guidopierri.pantrybe.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    public long id;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
}
