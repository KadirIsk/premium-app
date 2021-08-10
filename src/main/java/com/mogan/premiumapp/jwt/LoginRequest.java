package com.mogan.premiumapp.jwt;

import lombok.Data;

@Data
public class LoginRequest {
    String username, password;
}
