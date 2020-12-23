package com.example.FlowFireHub.Services;

import com.example.FlowFireHub.Domains.AuthToken;
import com.example.FlowFireHub.Auth.IValues;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JwtUserDetailsService {

    @Autowired
    UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<com.example.FlowFireHub.Domains.User> foundUser = userRepository.findByUsername(username);
//        if (foundUser.isPresent()) {
//            System.out.println("FOund user");
//            return new User(foundUser.get().getUsername(), foundUser.get().getPassword(),
//                    new ArrayList<>());
//        } else {
//            throw new UsernameNotFoundException("User not found with username: " + username);
//        }
//    }

    public User authenticateToken(String token) throws ServletException {
        User result = null;

        SecurityContext context = SecurityContextHolder.getContext();
        Jws<Claims> claims = Jwts.parser().requireIssuer(IValues.ISSUER).setSigningKey(IValues.SECRET_KEY).parseClaimsJws(token);
        String user = (String) claims.getBody().get("usr");
        String roles = (String) claims.getBody().get("rol");
        List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
        for (String role : roles.split(","))
            authority.add(new SimpleGrantedAuthority(role));

        AuthToken authenticationTkn = new AuthToken(user, null, authority);
        result = userRepository.findByUsername(user).get();
        context.setAuthentication(authenticationTkn);

        return result;
    }
}
