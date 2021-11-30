package com.jm.demo.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jm.demo.data.model.User;
import com.jm.demo.service.UserService;
import com.jm.demo.util.JwtUtil;
import com.jm.demo.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!isJson(request) && !isFormUrlEncoded(request)) {
            throw new BadCredentialsException("Don't use content type for " + request.getContentType());
        }
        String username = "";
        String password = "";
        if (isJson(request)) {
            User user = new ObjectMapper().readValue(request.getReader(), User.class);
            username = user.getUsername();
            password = user.getPassword();
        }
        if (isFormUrlEncoded(request)) {
            username = request.getParameter("username");
            password = request.getParameter("password");
        }
        if (!StringUtils.hasText(username) || !StringUtils.hasText(password)) {
            throw new BadCredentialsException("Username or password not sent");
        }
        UsernamePasswordAuthenticationToken authRequest =
                new UsernamePasswordAuthenticationToken(username, password);
        return authenticationManager.authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authentication) throws IOException {
        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        final String accessToken = JwtUtil.createToken(user);
        final String refreshToken = JwtUtil.createRefreshToken(user);
        ResponseUtil.responseTokensWithUserInfo(response, accessToken, refreshToken, userService.findByUsername(user.getUsername()));
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException {
        ResponseUtil.responseBadCredentials(response, failed);
    }

    private boolean isJson(HttpServletRequest request) {
        return MediaType.APPLICATION_JSON_VALUE.equalsIgnoreCase(request.getContentType());
    }

    private boolean isFormUrlEncoded(HttpServletRequest request) {
        return MediaType.APPLICATION_FORM_URLENCODED_VALUE.equalsIgnoreCase(request.getContentType());
    }
}
