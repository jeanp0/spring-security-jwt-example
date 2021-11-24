package com.jm.demo.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jm.demo.config.constants.Keys;
import com.jm.demo.data.model.User;
import org.springframework.security.core.AuthenticationException;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;

import static com.jm.demo.config.constants.Names.HEADER_ERROR;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

public class ResponseUtil {

    public static void responseGenericForbidden(Exception exception, HttpServletResponse response) throws IOException {
        response.setHeader(HEADER_ERROR, exception.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> payload = Map.of(Keys.MESSAGE_KEY, exception.getMessage());
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), payload);
    }

    public static void responseInvalidToken(Exception exception, HttpServletResponse response) throws IOException {
        response.setHeader(HEADER_ERROR, exception.getMessage());
        response.setStatus(FORBIDDEN.value());
        Map<String, String> payload = Map.of(Keys.MESSAGE_KEY, "Invalid session. Please login again.");
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), payload);
    }

    public static void responseBadCredentials(HttpServletResponse response, AuthenticationException failed)
            throws IOException {
        response.setHeader(HEADER_ERROR, failed.getMessage());
        response.setStatus(FORBIDDEN.value());
        String message = "Username or password incorrect";
        Map<String, String> payload = Map.of(Keys.MESSAGE_KEY, message);
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), payload);
    }

    public static void responseTokens(HttpServletResponse response, String accessToken, String refreshToken)
            throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), JwtUtil.mapTokens(accessToken, refreshToken));
    }

    public static void responseTokensWithUserInfo(HttpServletResponse response, String accessToken, String refreshToken,
            User user) throws IOException {
        response.setContentType(APPLICATION_JSON_VALUE);
        Map<String, String> payload = new java.util.HashMap<>(JwtUtil.mapTokens(accessToken, refreshToken));
        payload.put("uid", user.getUserId().toString());
        payload.put("username", user.getUsername());
        payload.put("email", user.getEmail());
        payload.put("name", user.getName());
        new ObjectMapper().writeValue(response.getOutputStream(), payload);
    }
}
