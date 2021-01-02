package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Auth.IValues;
import com.example.FlowFireHub.Domains.FlowFire;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.FlowFireRepository;
import com.example.FlowFireHub.Repositories.UserRepository;
import com.example.FlowFireHub.Services.JwtUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin
@RestController
public class AuthController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlowFireRepository flowFireRepository;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody FlowFire user) throws ServletException {

        String jwttoken = "";
        Map<String, String> response = new HashMap();

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty())
            return new ResponseEntity<String>("Username or password cannot be empty.", HttpStatus.BAD_REQUEST);

        Optional<FlowFire> checkForUser = flowFireRepository.findByUsername(user.getUsername());
        User userObject = userRepository.findByUsername(user.getUsername()).get();
        String role = userObject.getRole().getName();

        if (!checkForUser.isPresent())
            return new ResponseEntity<String>("Invalid username.", HttpStatus.UNAUTHORIZED);
        else {
            if(bCryptPasswordEncoder.matches(user.getPassword(), checkForUser.get().getPassword())) {
                // Creating JWT using the user credentials.
                Map<String, Object> claims = new HashMap<String, Object>();
                claims.put("usr", user.getUsername());
                claims.put("sub", "Authentication token");
                claims.put("iss", IValues.ISSUER);
                claims.put("rol", role);
                claims.put("iat", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                jwttoken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, IValues.SECRET_KEY).compact();


                response.put("token", jwttoken);
                response.put("username", userObject.getUsername());
                response.put("id", userObject.getId().toString());

                System.out.println("Returning token: " + jwttoken);
            } else {
                return new ResponseEntity<String>("Invalid password.", HttpStatus.FORBIDDEN);
            }
        }

        return new ResponseEntity(response, HttpStatus.OK);
    }


    @PostMapping("/me")
    public ResponseEntity<User> getLoggedInUser(@RequestHeader("Authorization") String bearer) {
        String token = bearer.split(" ")[1];
        try {
            Jws<Claims> claims = Jwts.parser().requireIssuer(IValues.ISSUER).setSigningKey(IValues.SECRET_KEY).parseClaimsJws(token);
            String username = (String) claims.getBody().get("usr");
            User user = userRepository.findByUsername(username).get();
            user.setRole(user.getRole());
            return new ResponseEntity<User>(user, HttpStatus.OK);
        } catch (SignatureException e) {
            return new ResponseEntity(null, HttpStatus.OK);
        }
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
