package com.jm.demo.auth;

import com.jm.demo.util.JwtUtil;
import com.jm.demo.util.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.jm.demo.constants.Names.BEARER;
import static com.jm.demo.constants.Paths.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        boolean isLoginOrRefresh =
                request.getServletPath().equals(LOGIN_PATH) || request.getServletPath().equals(REFRESH_TOKEN_PATH);
        if (isLoginOrRefresh) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = request.getHeader(AUTHORIZATION);
        boolean hasToken = authorizationHeader != null && authorizationHeader.startsWith(BEARER);
        if (!hasToken) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String token = authorizationHeader.substring(BEARER.length());
            String username = JwtUtil.getSubject(token);
            String[] roles = JwtUtil.getClaim(token, JwtInfo.ROLES_CLAIM_KEY).asArray(String.class);
            List<SimpleGrantedAuthority> authorities = Arrays.stream(roles)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);
            filterChain.doFilter(request, response);
        } catch (Exception exception) {
            log.error("Error logging in {}", exception.getMessage());
            ResponseUtil.responseGenericForbidden(exception, response);
        }
    }
}
