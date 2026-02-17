package com.valkyrie.authentication.service;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.valkyrie.authentication.config.TokenConfig;
import com.valkyrie.authentication.model.Authenticator;
import com.valkyrie.authentication.repository.AuthenticatorRepo;

@Service
public class AuthenticatorService {
    private AuthenticatorRepo repo;
    @Autowired
    private void setRepo(AuthenticatorRepo repo) {
        this.repo = repo;
    }

    private AuthenticationManager authManager;
    @Autowired
    private void setAuthManager(AuthenticationManager authManager) {
        this.authManager = authManager;
    }

    private TokenConfig config;
    @Autowired
    private void setConfig(TokenConfig config) {
        this.config = config;
    }

    public ResponseEntity<Boolean> signUp(Authenticator user) {
        user.setPassword(new BCryptPasswordEncoder(12).encode(user.getPassword()))
            .setAuthenticator("ROLE_" + user.getRole().toUpperCase());
        boolean isExist = repo.existsById(user.getUsername());

        if (isExist) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isExist); 
        }

        repo.save(user);
        isExist = repo.existsById(user.getUsername());

        if (isExist) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(isExist);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(isExist);
        }

    }

    public ResponseEntity<String> signIn(Authenticator user) {
        String token = null;
        Authentication authentication = authManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
        );

        if (authentication.isAuthenticated()) {
            token = config.generateToken(user.getUsername(), authentication.getAuthorities());

            return ResponseEntity.status(HttpStatus.OK).body(token);
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }

    public ResponseEntity<Authenticator> getUser(String username) {
        // System.out.println("True");
        username = new String(Base64.getDecoder().decode(username));
        Boolean isExist = repo.existsById(username);

        if (isExist) {
            return ResponseEntity.status(HttpStatus.OK).body(repo.findById(username).orElse(null));
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    
    public ResponseEntity<Boolean> removeUser(Authenticator user) {
        Boolean isExist = repo.existsById(user.getUsername());

        if (isExist) {
            Authenticator presentUser = repo.findById(user.getUsername()).orElse(null);

            if (presentUser != null) {
                BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
                
                if (encoder.matches(user.getPassword(), presentUser.getPassword())) {
                    repo.deleteById(user.getUsername());
                    isExist = repo.existsById(user.getUsername());

                    if (!isExist) {
                        return ResponseEntity.status(HttpStatus.ACCEPTED).body(true);
                    } else {
                        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
                    }

                } else {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
                }

            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
            }

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
        
    }
    
}

