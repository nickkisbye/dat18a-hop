package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth", path = "/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;

    // jwt impl

    @PostMapping("/")
    public Object validateUser(@RequestBody User user) {
        User validateUser = userRepository.validateUser(user.getUsername(), user.getPassword());
        if(validateUser != null) {
            // give token
        } else {
            // return error message
        }
        return new Object();
    }
}
