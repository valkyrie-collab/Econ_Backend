package com.valkyrie.authentication.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.valkyrie.authentication.model.Authenticator;
import com.valkyrie.authentication.service.AuthenticatorService;

@RestController
@RequestMapping("/authentication")
public class AuthenticatorController {
    private AuthenticatorService service;
    @Autowired
    private void setService(AuthenticatorService service) {
        this.service = service;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<Boolean> signUp(@RequestBody Authenticator user) {
        return service.signUp(user);
    }
    
    @PostMapping("/sign-in")
    public ResponseEntity<String> signIn(@RequestBody Authenticator user) {
        return service.signIn(user);
    }

    @GetMapping("/find-user")
    public ResponseEntity<Authenticator> findUser(@RequestParam String username) {
        return service.getUser(username);
    }

    @DeleteMapping("/remove-user")
    public ResponseEntity<Boolean> removeUser(@RequestBody Authenticator user) {
        return service.removeUser(user);
    }
}
