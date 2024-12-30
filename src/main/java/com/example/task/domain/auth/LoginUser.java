package com.example.task.domain.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class LoginUser extends User {
        public LoginUser(String userId, String password, Collection<? extends GrantedAuthority> authorities) {
                super(userId, password, authorities);
        }
}
