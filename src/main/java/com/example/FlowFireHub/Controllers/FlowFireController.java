package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.FlowFire;
import com.example.FlowFireHub.Domains.Role;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.FlowFireRepository;
import com.example.FlowFireHub.Repositories.RoleRepository;
import com.example.FlowFireHub.Utilities.SteamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/flowfire", path = "/flowfire")
public class FlowFireController {

    @Autowired
    FlowFireRepository flowFireRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    SteamManager steamManager;
    @Autowired
    RoleRepository roleRepository;


    @GetMapping("/getAllUsers")
    public Iterable<FlowFire> getAllUsers() {
        Iterable<FlowFire> fireflow = flowFireRepository.findAll();
        return fireflow;
    }

    @GetMapping("/getUser/{id}")
    public Optional<FlowFire> getUserById(@PathVariable("id") Long id) {
        Optional<FlowFire> fireFlow = flowFireRepository.findById(id);
        if (fireFlow.isPresent()) {
            return fireFlow;
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<FlowFire> deleteUser(@PathVariable("id") Long id) {
        try {
            flowFireRepository.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<FlowFire> addUser(@RequestBody FlowFire fireFlow) {
        Optional<FlowFire> userToAdd = flowFireRepository.findByUsername(fireFlow.getUsername());
        if (!userToAdd.isPresent()) {
            Role role = roleRepository.findByName("Admin");
            fireFlow.setUser(new User(fireFlow.getUsername(), role));
            fireFlow.setPassword(bCryptPasswordEncoder.encode(fireFlow.getPassword()));
            return new ResponseEntity<FlowFire>(flowFireRepository.save(fireFlow), HttpStatus.OK);
        } else {
            return new ResponseEntity<FlowFire>(fireFlow, HttpStatus.NOT_ACCEPTABLE);
        }
    }


}
