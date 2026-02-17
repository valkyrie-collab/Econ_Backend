package com.valkyrie.authentication.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.valkyrie.authentication.model.Authenticator;

public class CustomUserDetails implements UserDetails{
    private Authenticator user;

    public CustomUserDetails(Authenticator user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole();

        if (user == null || role == null || role.isEmpty()) {
            return List.of();
        }

        String[] roles = role.split(",");

        return Arrays.stream(roles).map(String::trim)
            .filter(r -> !r.isEmpty()).map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

}
