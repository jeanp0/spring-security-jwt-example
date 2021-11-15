package com.jm.demo.controller;

import com.jm.demo.config.constants.Names;
import com.jm.demo.data.dto.ErrorResponseDto;
import com.jm.demo.exception.BadRequestException;
import com.jm.demo.util.JwtUtil;
import com.jm.demo.util.ResponseUtil;
import com.jm.demo.data.model.User;
import com.jm.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.jm.demo.config.constants.Paths.REFRESH_TOKEN_PATH;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@RestController
@RequestMapping
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Refresh access token", description = "Refresh access token description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "New access token successfully obtained"),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
    })
    @PostMapping(path = REFRESH_TOKEN_PATH)
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith(Names.BEARER)) {
            try {
                final String refreshToken = authorizationHeader.substring(Names.BEARER.length());
                final String username = JwtUtil.getSubject(refreshToken);
                final String issuer = request.getRequestURL().toString();
                final User user = userService.findByUsername(username);
                final String accessToken = JwtUtil.generateAccessToken(user, issuer);
                ResponseUtil.responseTokensWithUserInfo(response, accessToken, refreshToken, user);
            } catch (Exception exception) {
                log.error("Error refreshing token in {}", exception.getMessage());
                ResponseUtil.responseInvalidToken(exception, response);
            }
        } else {
            throw new BadRequestException("Refresh token is missing");
        }
    }
}
