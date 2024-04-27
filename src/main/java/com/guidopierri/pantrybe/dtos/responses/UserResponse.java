package com.guidopierri.pantrybe.dtos.responses;

public record UserResponse(long id,
                           String firstName,
                           String lastName,
                           String email,
                           String imageUrl,
                           String password,
                           String roles,
                           String token) {

}
