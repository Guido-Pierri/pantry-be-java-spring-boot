package com.guidopierri.pantrybe.dtos.requests;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(@NotNull long id,
                                @NotNull String firstName,
                                @NotNull String lastName,
                                @Email @NotNull String email,
                                @NotNull String password) {

}
