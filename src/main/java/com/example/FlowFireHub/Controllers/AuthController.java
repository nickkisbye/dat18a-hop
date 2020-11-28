package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(value = "/auth", path = "/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    // jwt impl

    @PostMapping("/")
    public ResponseEntity<?> validateUser(@RequestBody User user) throws Exception {
        User validateUser = userRepository.validateUser(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()));
        if(validateUser != null) {

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

}
