package com.valkyrie.product_service.config;

import java.security.Key;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenConfig {
    @Value("${jwts.security}")
    private String securityKey;

    private Key generateKey() {return Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));}

    private <I> I getClaims(String token, Function<Claims, I> getClaims) {
        return getClaims.apply(
            Jwts.parserBuilder().setSigningKey(generateKey()).build()
                .parseClaimsJws(token).getBody()
        );
    }

    public String getUsername(String token) {
        return getClaims(token, Claims::getSubject);
    }
}
