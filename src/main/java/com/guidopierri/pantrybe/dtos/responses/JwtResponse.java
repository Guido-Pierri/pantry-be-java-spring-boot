package com.guidopierri.pantrybe.dtos.responses;

public record JwtResponse(String jwtToken, String username) {
    //Builder
    public static final class Builder {

        String jwtToken;
        String username;


        /*public Builder(String jwtToken, String username) {
            this.jwtToken = jwtToken;
            this.username = username;
        }*/


        public Builder jwtToken(String jwtToken) {
            this.jwtToken = jwtToken;
            return this;
        }
        public Builder username(String username) {
            this.username = username;
            return this;
        }


        public JwtResponse build() {
            return new JwtResponse(jwtToken, username);
        }


    }
}
