package com.valkyrie.api.config;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.valkyrie.api.model.Authenticator;

@Component
public class CustomUserDetailsService implements UserDetailsService{
    private AuthenticationFeignController feignCtrl;
    @Autowired
    private void setFeignCtrl(AuthenticationFeignController feignCtrl) {
        this.feignCtrl = feignCtrl;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Authenticator user = feignCtrl.findUser(Base64.getEncoder().encodeToString(username.getBytes())).getBody();

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }

        return new CustomUserDetails(user);
    }

}
