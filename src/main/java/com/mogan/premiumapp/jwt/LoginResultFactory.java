package com.mogan.premiumapp.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.mogan.premiumapp.model.User;

import java.util.ArrayList;
import java.util.List;

public class LoginResultFactory {
    private static final String SECRET = "THIS IS USED TO SIGN AND VERIFY JWT TOKENS, " +
            "REPLACE IT WITH YOUR OWN SECRET, IT CAN BE ANY STRING NEW8";

    public static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    public static LoginResult createLoginResult(User user) {
        return LoginResult.builder()
                          .result(true)
                          .user(createUserDTO(user))
                          .token(createToken(user.getId(), getRoller(user)))
                          .build();
    }

    private static List<String> getRoller(User user) {
        List<String> list = new ArrayList<>();
//        for (Role rol : kullanici.getRoles())
//            list.add(rol.getRole());
        return list;
    }

    private static String createAccessToken(long userId, List<String> roles) {
        return JWT.create()
                  .withSubject(String.valueOf(userId))
                  .withArrayClaim("roles", roles.toArray(new String[0]))
                  .sign(ALGORITHM);
    }

    private static LoginResult.UserDTO createUserDTO(User user) {
        return LoginResult.UserDTO.builder()
                                  .id(user.getId())
                                  .username(user.getUsername())
//                                  .name(user.getName())
//                                  .surname(user.getSurname()).roles(user.getRoles())
                                  .build();
    }

    private static LoginResult.Token createToken(long kullaniciId, List<String> roller) {
        return LoginResult.Token.builder()
                                .access_token(createAccessToken(kullaniciId, roller))
                                .build();
    }
}
