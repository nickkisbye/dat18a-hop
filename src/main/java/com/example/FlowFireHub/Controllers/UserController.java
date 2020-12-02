package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users", path = "/users")
public class UserController {

//    @Autowired
//    UserRepository userRepository;
//    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

//    @GetMapping("/")
//    public Iterable<User> getAllUsers() {
//        Iterable<User> users = userRepository.findAll();
//        return users;
//    }
//
//    @GetMapping("/{id}")
//    public Optional<User> getUserById(@PathVariable("id") Long id) {
//        Optional<User> user = userRepository.findById(id);
//        if(user.isPresent()) {
//            return user;
//        } else {
//            return null;
//        }
//    }
//
//    @PostMapping("/")
//    public ResponseEntity<User> addUser(@RequestBody User user) {
//        Optional<User> userToAdd = userRepository.findByEmailOrUsername(user.getUsername(), user.getEmail());
//        if(!userToAdd.isPresent()) {
//            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            return new ResponseEntity<User>(userRepository.save(user), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<User>(user, HttpStatus.NOT_ACCEPTABLE);
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<User> deleteUser(@PathVariable("id") Long id) {
//        try {
//            userRepository.deleteById(id);
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("id") Long id) {
//        Optional<User> userToUpdate = userRepository.findById(id);
//        if(userToUpdate.isPresent()) {
//            User _user = userToUpdate.get();
//            _user.setFirstName(user.getFirstName());
//            _user.setEmail(user.getEmail());
//            _user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//            return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
}
