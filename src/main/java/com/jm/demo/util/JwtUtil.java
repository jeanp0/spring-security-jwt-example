package com.jm.demo.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.jm.demo.auth.JwtInfo;
import com.jm.demo.data.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtUtil {

    public static Map<String, String> mapTokens(String accessToken, String refreshToken) {
        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    public static String createToken(UserDetails userDetails) {
        return createToken(userDetails, DateUtil.nowAfterHoursToDate(JwtInfo.EXPIRES_LIMIT));
    }

    public static String createToken(User user) {
        return createToken(user, DateUtil.nowAfterHoursToDate(JwtInfo.EXPIRES_LIMIT));
    }

    public static String createToken(UserDetails userDetails, Date date) {
        return JWT.create()
                .withIssuer(JwtInfo.ISSUER)
                .withClaim(JwtInfo.ROLES_CLAIM_KEY, userDetails.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .withSubject(userDetails.getUsername())
                .withExpiresAt(date)
                .sign(JwtInfo.getAlgorithm());
    }

    public static String createToken(User user, Date date) {
        return JWT.create()
                .withIssuer(JwtInfo.ISSUER)
                .withClaim(JwtInfo.ROLES_CLAIM_KEY, user.getRolesName())
                .withSubject(user.getUsername())
                .withExpiresAt(date)
                .sign(JwtInfo.getAlgorithm());
    }

    public static String createRefreshToken(UserDetails userDetails) {
        return createToken(userDetails, DateUtil.nowAfterDaysToDate(JwtInfo.EXPIRES_LIMIT));
    }

    public static Boolean verify(String token) {
        try {
            JWTVerifier verifier = JWT.require(JwtInfo.getAlgorithm()).build();
            verifier.verify(token);
            return Boolean.TRUE;
        } catch (JWTVerificationException verifyEx) {
            return Boolean.FALSE;
        }
    }

    public static DecodedJWT decodeToken(String token) {
        Algorithm algorithm = Algorithm.HMAC256(JwtInfo.TOKEN_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public static String getSubject(String token) {
        return decodeToken(token).getSubject();
    }

    public static Claim getClaim(String token, String key) {
        return decodeToken(token).getClaim(key);
    }
}
