package com.example.FlowFireHub.Auth;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.example.FlowFireHub.Domains.AuthToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authenticationHeader = request.getHeader(IValues.HEADER);

        try {
            SecurityContext context = SecurityContextHolder.getContext();

            if (authenticationHeader != null && authenticationHeader.startsWith("Bearer")) {

                final String token = authenticationHeader.replaceAll(IValues.BEARER_TOKEN, "");

                try {

                    Jws<Claims> claims = Jwts.parser().requireIssuer(IValues.ISSUER).setSigningKey(IValues.SECRET_KEY).parseClaimsJws(token);

                    String user = (String) claims.getBody().get("usr");
                    String role = (String) claims.getBody().get("rol");

                    List<GrantedAuthority> authority= new ArrayList<GrantedAuthority>();

                    authority.add(new SimpleGrantedAuthority(role));

                    AuthToken authenticationTkn= new AuthToken(user, null, authority);
                    context.setAuthentication(authenticationTkn);
                } catch (SignatureException e) {
                    throw new ServletException("Invalid token.");
                }
            }

            filterChain.doFilter(request, response);
            context.setAuthentication(null);

        } catch(AuthenticationException ex) {
            throw new ServletException("Authentication exception.");
        }
    }
}