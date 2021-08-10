package com.mogan.premiumapp.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.mogan.premiumapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.mogan.premiumapp.jwt.LoginResultFactory.ALGORITHM;

@Component
public class JWTFilter extends OncePerRequestFilter {
    public static final String JWT_LOGIN_URL = "/api/authentication/login";
    public static final String JWT_REGISTER_URL = "/api/authentication/register";

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        if (!isExcluded(request)) {
            String header = request.getHeader("Authorization");
            if (header == null || !header.startsWith("Bearer ")) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }

            String token = header.substring("Bearer ".length());

            String subject = verify(token);

            long userId = Long.parseLong(subject);

            if (!userRepository.findById(userId).isPresent()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    private boolean isExcluded(HttpServletRequest request) {
        List<String> excludedPrefixes = Arrays.asList(JWT_LOGIN_URL, JWT_REGISTER_URL);
        return excludedPrefixes.stream()
                               .anyMatch(prefix -> request.getRequestURI().startsWith(prefix));
    }

    public String verify(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM)
//                    .withIssuer("auth0")
                                  .build(); //Reusable verifier instance

        DecodedJWT jwt = verifier.verify(token);

        // jwt.getIssuedAt() TODO

        return jwt.getSubject();
    }
}
