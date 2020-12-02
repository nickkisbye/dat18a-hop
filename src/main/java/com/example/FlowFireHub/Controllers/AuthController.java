package com.example.FlowFireHub.Controllers;

//import com.example.FlowFireHub.Auth.JWTUtility;
import com.example.FlowFireHub.Domains.JwtResponse;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.UserRepository;
import com.example.FlowFireHub.Services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.DisabledException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin
//@RequestMapping(value = "/auth", path = "/auth")
public class AuthController {

//    @Autowired
//    UserRepository userRepository;
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    @Autowired
//    private JWTUtility jwtUtility;

//    @Autowired
//    private JwtUserDetailsService userDetailsService;
//
//    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // jwt impl
//
//    @PostMapping("/")
//    public ResponseEntity<?> validateUser(@RequestBody User user) throws Exception {
//
//            authenticate(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()));
//
//            final UserDetails userDetails = userDetailsService
//                    .loadUserByUsername(user.getUsername());
//
//            final String token = jwtUtility.generateToken(userDetails);
//        System.out.println("token: " + token);
//            return ResponseEntity.ok(new JwtResponse(token));
//    }
//
//    private void authenticate(String username, String password) throws Exception {
//        try {
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
//        } catch (DisabledException e) {
//            throw new Exception("USER_DISABLED", e);
//        } catch (BadCredentialsException e) {
//            throw new Exception("INVALID_CREDENTIALS", e);
//        }
//    }

}
