package com.valkyrie.api.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.valkyrie.api.model.Authenticator;

@FeignClient(value = "AUTHENTICATION", configuration = AuthenticationByPass.class)
public interface AuthenticationFeignController {
    @GetMapping("/authentication/find-user")
    public ResponseEntity<Authenticator> findUser(@RequestParam String username);
}
