package com.valkyrie.authentication.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.valkyrie.authentication.model.Authenticator;
import com.valkyrie.authentication.repository.AuthenticatorRepo;

@Component
public class CustomUserDetailsService implements UserDetailsService{
    private AuthenticatorRepo repo;
    @Autowired
    private void setRepo(AuthenticatorRepo repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authenticator user = repo.findById(username).orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(user);
    }

}
