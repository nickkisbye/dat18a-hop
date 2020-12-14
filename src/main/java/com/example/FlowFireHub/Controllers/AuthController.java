//package com.example.FlowFireHub.Controllers;
//
//import com.example.FlowFireHub.Auth.Iconstants;
//import com.example.FlowFireHub.Domains.User;
//import com.example.FlowFireHub.Respositories.UserRepository;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.SignatureAlgorithm;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.servlet.ServletException;
//import javax.swing.*;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.Optional;
//
//@RestController
//public class AuthController {
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @PostMapping("/token")
//    public ResponseEntity<String> getToken(@RequestBody User login) throws ServletException {
//
//        String jwttoken = "";
//
//        // If the username and password fields are empty -> Throw an exception!
//        if (login.getUsername().isEmpty() || login.getPassword().isEmpty())
//            return new ResponseEntity<String>("Username or password cannot be empty.", HttpStatus.BAD_REQUEST);
//
//        String name = login.getUsername(),
//                password = login.getPassword();
//
//        // If the username and password are not valid -> Thrown an invalid credentials exception!
//
//        Optional<User> checkForUser = userRepository.findByUsername(name);
//
//        if (!checkForUser.isPresent())
//            return new ResponseEntity<String>("Invalid username.", HttpStatus.UNAUTHORIZED);
//        else {
//            if(bCryptPasswordEncoder.matches(password, checkForUser.get().getPassword())) {
//                // Creating JWT using the user credentials.
//                Map<String, Object> claims = new HashMap<String, Object>();
//                claims.put("usr", login.getUsername());
//                claims.put("sub", "Authentication token");
//                claims.put("iss", Iconstants.ISSUER);
//                claims.put("rol", "Administrator, Developer");
//                claims.put("iat", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//
//                jwttoken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, Iconstants.SECRET_KEY).compact();
//                System.out.println("Returning the following token to the user= " + jwttoken);
//            } else {
//                return new ResponseEntity<String>("Invalid password.", HttpStatus.UNAUTHORIZED);
//            }
//        }
//
//        return new ResponseEntity<String>(jwttoken, HttpStatus.OK);
//    }
//
//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
