package com.auth.jwt.security;

import com.auth.jwt.dto.RequestDto;
import com.auth.jwt.entities.AuthUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtProvider {
    @Value(value = "${jwt.secret}")
    private String secret;
    private final RouteValidator routeValidator;

    public JwtProvider(RouteValidator routeValidator) {
        this.routeValidator = routeValidator;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getDecoder().toString();
    }

    public String createToken(AuthUser authUser) {
        Map<String, Object> claims = new HashMap<>();
        claims = (Map<String, Object>) Jwts.claims().setSubject(authUser.getUserName());
        claims.put("id", authUser.getId());
        claims.put("role", authUser.getRole());
        Date nowDate = new Date();
        Date expiration = new Date(nowDate.getTime() + 3600000);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(nowDate)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Boolean validateToken (String token, RequestDto requestDto) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
        } catch (Exception exception) {
            return false;
        }
        if (!isAdmin(token) && routeValidator.isAdmin(requestDto)) {
            return false;
        }
        return true;
    }

    public String getUserNameFromToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
        } catch (Exception exception) {
            return "Bad Token";
        }
    }

    private Boolean isAdmin (String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("role").equals("admin");
    }
}
