package com.iqy.im.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

@Slf4j
@Component
public class JwtUtil {
    private String secret = "im-netty";

    private long expiration = 2 * 60 * 60 * 1000;

    private final String FIELD_USERNAME = "userId";

    private final String FIELD_ROLES = "roles";

    private final String FIELD_PERMISSIONS = "permissions";

    public JwtToken generateToken(AuthUser authUser) {
        Map<String, Object> map = new HashMap<>();
        map.put(FIELD_USERNAME, authUser.getId());
        map.put(FIELD_ROLES, authUser.getRoles());
        map.put(FIELD_PERMISSIONS, authUser.getPermissions());
        String accessToken = generateToken(map, expiration);
        String refreshToken = generateToken(map, expiration * 2);
        return new JwtToken(accessToken, refreshToken);
    }

    private String generateToken(Map<String, Object> map, long expiration) {
        return Jwts.builder()
                .setClaims(map)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    public boolean verify(String token) {
        try {
            return !isExpired(token);
        } catch (Exception e) {
            log.error("jwt verify failed: {}", e.getLocalizedMessage());
            return false;
        }
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    public AuthUser getAuthUser(String token) {
        Claims claims = getClaims(token);
        String userId = claims.get(FIELD_USERNAME).toString();
        if (StringUtils.isEmpty(userId)) {
            return null;
        }
        List<String> roles = (List<String>) claims.get(FIELD_ROLES);
        List<String> permissions = (List<String>) claims.get(FIELD_PERMISSIONS);

        return AuthUser.builder()
                .id(userId)
                .roles(roles)
                .permissions(permissions)
                .build();
    }

}
