package com.valkyrie.authentication_service.config;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final CustomUserDetailsService service;
    private final BCryptPasswordEncoder ENCODER;

    private CustomAuthenticationProvider(CustomUserDetailsService service, BCryptPasswordEncoder ENCODER) {
        this.ENCODER = ENCODER; this.service = service;
    }

    public static CustomAuthenticationProvider initialize(
        CustomUserDetailsService service, BCryptPasswordEncoder ENCODER) {
            return new CustomAuthenticationProvider(service, ENCODER);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails userDetails = service.loadUserByUsername(username);

        if (!ENCODER.matches(password, userDetails.getPassword())) {
            throw new RuntimeException();
        }

        return new UsernamePasswordAuthenticationToken(userDetails,password, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
