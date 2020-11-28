package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/users", path = "/users")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public Iterable<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return users;
    }

    @GetMapping("/:id")
    public Optional<User> getUserById(@RequestParam("id") Long id) {
        Optional<User> user = userRepository.findById(id);
        return user;
    }

    @PostMapping("/")
    public User addUser(@RequestBody User user) {
        User newUser = userRepository.save(user);
        return newUser;
    }

    @DeleteMapping("/:id")
    public Map<String, String> deleteUser(@RequestParam("id") Long id) {
        userRepository.deleteById(id);
        Map<String, String> map = new HashMap();
        map.put("Deleted", "true");
        return map;
    }

    @PutMapping("/")
    public User updateUser(@RequestBody User user) {
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }
}
