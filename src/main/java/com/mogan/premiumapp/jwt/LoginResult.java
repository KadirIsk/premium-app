package com.mogan.premiumapp.jwt;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class LoginResult {
    boolean result;
    UserDTO user;
    Token token;

    @Data
    @Builder
    static class UserDTO {
        Long id;
        String username;
        String name;
        String surname;
//        List<Role> roles;
    }

    @Data
    @Builder
    static class Token {
        String access_token;
    }
}
