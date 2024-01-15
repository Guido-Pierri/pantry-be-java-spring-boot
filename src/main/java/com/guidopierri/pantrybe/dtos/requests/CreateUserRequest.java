package com.guidopierri.pantrybe.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    public long id;
    public String firstName;
    public String lastName;
    @Email
    @NotNull
    public String email;
    @NotNull
    public String password;
}
