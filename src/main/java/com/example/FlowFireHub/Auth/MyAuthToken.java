package com.example.FlowFireHub.Auth;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class MyAuthToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersionUID = 1L;

    public MyAuthToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public MyAuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}