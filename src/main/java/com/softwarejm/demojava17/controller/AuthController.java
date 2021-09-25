package com.softwarejm.demojava17.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.softwarejm.demojava17.model.Role;
import com.softwarejm.demojava17.model.User;
import com.softwarejm.demojava17.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static com.softwarejm.demojava17.config.Keys.*;
import static com.softwarejm.demojava17.config.Names.BEARER;
import static com.softwarejm.demojava17.config.Names.HEADER_ERROR;
import static com.softwarejm.demojava17.config.Paths.API_URI;
import static com.softwarejm.demojava17.config.Paths.REFRESH_TOKEN_URI;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(API_URI)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping(REFRESH_TOKEN_URI)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            try {
                String refreshToken = authorizationHeader.substring(BEARER.length());
                Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refreshToken);
                String username = decodedJWT.getSubject();
                User user = userService.getUser(username);
                final String accessToken = JWT.create()
                        .withSubject(user.getUsername())
                        .withExpiresAt(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim(ROLES_CLAIM_KEY, user.getRoles()
                                .stream()
                                .map(Role::getName)
                                .collect(Collectors.toList()))
                        .sign(algorithm);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("accessToken", accessToken);
                tokens.put("refreshToken", refreshToken);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception exception) {
                log.error("Error refreshing token in {}", exception.getMessage());
                response.setHeader(HEADER_ERROR, exception.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> tokens = new HashMap<>();
                tokens.put(ERROR_KEY, exception.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
