package com.valkyrie.api_gateway.config;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenConfig {
    private final static int EXPIRATION = 1000 * 60 * 60 * 30;

    @Value("${jwts.security}")
    private String securityKey;

    private Key generateKey() {return Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));}

    private <I> I getClaims(String token, Function<Claims, I> claimsBearer) {
        Claims claims = Jwts.parserBuilder().setSigningKey(generateKey())
                            .build().parseClaimsJws(token).getBody();
        
        return claimsBearer.apply(claims);
    }

    private boolean isNotExpired(String token) {
        return !getClaims(token, Claims::getExpiration).before(new Date());
    }

    public String generateToken(
        String username, Collection<? extends GrantedAuthority> authorities) {
            List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).toList();
            Map<String, Object> claims = new HashMap<>();
            claims.put("roles", roles);

            return Jwts.builder().setClaims(claims).setSubject(username)
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                        .signWith(generateKey()).compact();
    }

    public String getUsername(String token) {return getClaims(token, Claims::getSubject);}

    public boolean isValid(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(getUsername(token)) && isNotExpired(token);
    }
}
