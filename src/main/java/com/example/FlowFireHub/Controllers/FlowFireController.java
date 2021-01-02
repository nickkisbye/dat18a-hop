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
    private FlowFireRepository flowFireRepository;
    @Autowired
    private SteamManager steamManager;
    @Autowired
    private RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @PostMapping("/addUser")
    public ResponseEntity<FlowFire> addUser(@RequestBody FlowFire flowFire) {
        Optional<FlowFire> userToAdd = flowFireRepository.findByUsername(flowFire.getUsername());
        if (!userToAdd.isPresent()) {
            Role role = roleRepository.findByName("Administrator");
            flowFire.setUser(new User(flowFire.getUsername(), role));
            flowFire.setPassword(bCryptPasswordEncoder.encode(flowFire.getPassword()));
            return new ResponseEntity<FlowFire>(flowFireRepository.save(flowFire), HttpStatus.OK);
        } else {
            return new ResponseEntity<FlowFire>(flowFire, HttpStatus.NOT_ACCEPTABLE);
        }
    }

    @GetMapping("/getAllUsers")
    public Iterable<FlowFire> getAllUsers() {
        Iterable<FlowFire> flowFire = flowFireRepository.findAll();
        return flowFire;
    }

    @GetMapping("/getUser/{id}")
    public Optional<FlowFire> getUserById(@PathVariable("id") Long id) {
        Optional<FlowFire> flowFire = flowFireRepository.findById(id);
        if (flowFire.isPresent()) {
            return flowFire;
        } else {
            return null;
        }
    }

    @PutMapping("/updateUser/{id}")
    public ResponseEntity<FlowFire> updateUser(@RequestBody FlowFire flowfire, @PathVariable("id") Long id) {
        Optional<FlowFire> userToUpdate = flowFireRepository.findById(id);
        if(userToUpdate.isPresent()) {
            FlowFire _user = userToUpdate.get();
            _user.setUsername(flowfire.getUsername());
            _user.setPassword(bCryptPasswordEncoder.encode(flowfire.getPassword()));
            return new ResponseEntity<>(flowFireRepository.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}
