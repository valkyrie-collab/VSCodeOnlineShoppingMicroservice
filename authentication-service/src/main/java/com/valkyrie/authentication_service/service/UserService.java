package com.valkyrie.authentication_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.valkyrie.authentication_service.config.TokenConfig;
import com.valkyrie.authentication_service.model.Store;
import com.valkyrie.authentication_service.model.User;
import com.valkyrie.authentication_service.repository.UserRepository;

@Service
public class UserService {
    private static final BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder(12);

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {this.config = config;}

    private AuthenticationManager authenticationManager;
    @Autowired
    private void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    private UserRepository repo;
    @Autowired
    private void setRepo(UserRepository repo) {this.repo = repo;}

    public Store<String> signIn(User user) {
        
        if (repo.findById(user.getUsername()).orElse(null) != null) {
            return Store.initialize(HttpStatus.OK, "The User present plz logIn....");
        }

        repo.save(user.setPassword(ENCODER.encode(user.getPassword())));

        return Store.initialize(HttpStatus.ACCEPTED, "The user saved Successfully plz logIn Now.....");
    }

    public Store<String> logIn(User user) {
        String token = null;
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            token = config.generateToken(user.getUsername(), authentication.getAuthorities());
            return Store.initialize(HttpStatus.OK, token);
        }

        return Store.initialize(HttpStatus.BAD_REQUEST, token);
    }

    public Store<User> getUser(String username) {
        return Store.initialize(HttpStatus.OK, repo.findById(username).orElse(null));
    }
}
