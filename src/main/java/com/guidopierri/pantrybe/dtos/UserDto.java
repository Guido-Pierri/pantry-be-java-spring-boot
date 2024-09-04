package com.guidopierri.pantrybe.dtos;

public record UserDto(long id, String firstName, String lastName, String email, String imageUrl, String roles,
                      boolean isFirstTimeUser) {

}
