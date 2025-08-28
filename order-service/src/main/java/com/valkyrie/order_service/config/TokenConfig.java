package com.valkyrie.order_service.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.function.Function;

@Component
public class TokenConfig {
    @Value("${jwts.security}")
    private String securityKey;

    private Key generateKey() {return Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));}

    private <I> I getClaims(String token, Function<Claims, I> claimsBearer) {
        return claimsBearer.apply(
                Jwts.parserBuilder().setSigningKey(generateKey()).build().parseClaimsJws(token).getBody()
        );
    }

    public String getUsername(String token) {return getClaims(token, Claims::getSubject);}
}
