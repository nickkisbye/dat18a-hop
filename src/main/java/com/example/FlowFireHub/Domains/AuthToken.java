package com.example.FlowFireHub.Domains;

import java.util.Collection;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class AuthToken extends UsernamePasswordAuthenticationToken {

    private static final long serialVersion = 1L;

    public AuthToken(Object principal, Object credentials) {
        super(principal, credentials);
    }

    public AuthToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(principal, credentials, authorities);
    }
}