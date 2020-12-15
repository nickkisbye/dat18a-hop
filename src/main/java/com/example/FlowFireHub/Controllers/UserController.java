package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.RoleRepository;
import com.example.FlowFireHub.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users", path = "/users")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;


    @GetMapping("/getAllUsers")
    public Iterable<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return users;
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") Long id) {
        Optional<User> optinalEntity =  userRepository.findById(id);
        if(optinalEntity.isPresent()) {
            User user = optinalEntity.get();
            return new ResponseEntity<>(user,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
        System.out.println(id);
        try {
            userRepository.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

//    @PutMapping("/updateUser/{id}")
//    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
//        Optional<User> userToUpdate = userRepository.findById(id);
//        if(userToUpdate.isPresent()) {
//            User _user = userToUpdate.get();
//            _user.setUsername(user.getUsername());
//            _user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
