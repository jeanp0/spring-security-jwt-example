package com.softwarejm.demojava17.controller;

import com.softwarejm.demojava17.model.User;
import com.softwarejm.demojava17.service.UserService;
import com.softwarejm.demojava17.util.JwtUtil;
import com.softwarejm.demojava17.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.softwarejm.demojava17.config.Names.BEARER;
import static com.softwarejm.demojava17.config.Paths.API_PATH;
import static com.softwarejm.demojava17.config.Paths.REFRESH_TOKEN_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping(API_PATH)
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping(REFRESH_TOKEN_PATH)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(BEARER)) {
            try {
                String refreshToken = authorizationHeader.substring(BEARER.length());
                String username = JwtUtil.getSubject(refreshToken);
                String issuer = request.getRequestURL().toString();
                User user = userService.getUser(username);
                final String accessToken = JwtUtil.generateAccessToken(user, issuer);
                ResponseUtil.responseTokensWithUserInfo(response, accessToken, refreshToken, user);
            } catch (Exception exception) {
                log.error("Error refreshing token in {}", exception.getMessage());
                ResponseUtil.responseInvalidToken(exception, response);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }
}
