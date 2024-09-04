package com.guidopierri.pantrybe.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.URL;

public record CreateUserRequest(@NotNull long id,
                                @NotNull String firstName,
                                @NotNull String lastName,
                                @Email @NotNull String email,
                                @URL /*@NotNull*/ String imageUrl,
                                @NotNull String password,
                                @NotNull String roles,
                                @NotNull String authProvider,
                                boolean isFirstTimeUser) {

}
