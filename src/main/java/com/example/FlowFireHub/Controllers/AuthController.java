package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Auth.IValues;
import com.example.FlowFireHub.Domains.FireFlow;
import com.example.FlowFireHub.Respositories.FireFlowRepository;
import com.example.FlowFireHub.Respositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
public class AuthController {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FireFlowRepository fireFlowRepository;

    @PostMapping("/token")
    public ResponseEntity<String> getToken(@RequestBody FireFlow user) throws ServletException {
        String jwttoken = "";

        if (user.getUsername().isEmpty() || user.getPassword().isEmpty())
            return new ResponseEntity<String>("Username or password cannot be empty.", HttpStatus.BAD_REQUEST);

        Optional<FireFlow> checkForUser = fireFlowRepository.findByUsername(user.getUsername());

        if (!checkForUser.isPresent())
            return new ResponseEntity<String>("Invalid username.", HttpStatus.UNAUTHORIZED);
        else {
            if(bCryptPasswordEncoder.matches(user.getPassword(), checkForUser.get().getPassword())) {
                // Creating JWT using the user credentials.
                Map<String, Object> claims = new HashMap<String, Object>();
                claims.put("usr", user.getUsername());
                claims.put("sub", "Authentication token");
                claims.put("iss", IValues.ISSUER);
                claims.put("rol", "Administrator, Developer");
                claims.put("iat", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

                jwttoken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, IValues.SECRET_KEY).compact();
                System.out.println("Returning the following token to the user= " + jwttoken);
            } else {
                return new ResponseEntity<String>("Invalid password.", HttpStatus.UNAUTHORIZED);
            }
        }

        return new ResponseEntity<String>(jwttoken, HttpStatus.OK);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
