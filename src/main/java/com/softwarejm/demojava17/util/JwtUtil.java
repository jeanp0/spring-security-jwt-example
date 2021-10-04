package com.softwarejm.demojava17.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.softwarejm.demojava17.model.Role;
import com.softwarejm.demojava17.model.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static com.softwarejm.demojava17.config.Keys.ROLES_CLAIM_KEY;
import static com.softwarejm.demojava17.config.Keys.SECRET_KEY;

public class JwtUtil {

    private static final Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());

    public static Map<String, String> mapTokens(String accessToken, String refreshToken) {
        return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
    }

    public static String generateAccessToken(User user, String issuer) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                .withIssuer(issuer)
                .withClaim(ROLES_CLAIM_KEY, user.getRolesName())
                .sign(algorithm);
    }

    public static String generateAccessToken(org.springframework.security.core.userdetails.User user, String issuer) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (10 * 60 * 1000)))
                .withIssuer(issuer)
                .withClaim(ROLES_CLAIM_KEY, user.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
    }

    public static String generateRefreshToken(org.springframework.security.core.userdetails.User user, String issuer) {
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 60 * 1000)))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public static DecodedJWT getDecodedJwt(String token) {
        Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        return verifier.verify(token);
    }

    public static String getSubject(String token) {
        return getDecodedJwt(token).getSubject();
    }

    public static Claim getClaim(String token, String key) {
        return getDecodedJwt(token).getClaim(key);
    }
}
