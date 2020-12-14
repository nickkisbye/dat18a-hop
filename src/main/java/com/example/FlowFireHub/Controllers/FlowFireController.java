package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.FireFlow;
import com.example.FlowFireHub.Domains.Role;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.FireFlowRepository;
import com.example.FlowFireHub.Respositories.RoleRepository;
import com.example.FlowFireHub.Utilities.SteamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "/fireflow", path = "/fireflow")
public class FlowFireController {

    @Autowired
    FireFlowRepository fireFlowRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
    @Autowired
    SteamManager steamManager;
    @Autowired
    RoleRepository roleRepository;


    @GetMapping("/getAllUsers")
    public Iterable<FireFlow> getAllUsers() {
        Iterable<FireFlow> fireflow = fireFlowRepository.findAll();
        return fireflow;
    }

    @GetMapping("/getUser/{id}")
    public Optional<FireFlow> getUserById(@PathVariable("id") Long id) {
        Optional<FireFlow> fireFlow = fireFlowRepository.findById(id);
        if (fireFlow.isPresent()) {
            return fireFlow;
        } else {
            return null;
        }
    }

    @DeleteMapping("/deleteUser/{id}")
    public ResponseEntity<FireFlow> deleteUser(@PathVariable("id") Long id) {
        try {
            fireFlowRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/addUser")
    public ResponseEntity<FireFlow> addUser(@RequestBody FireFlow fireFlow) {
        Optional<FireFlow> userToAdd = fireFlowRepository.findByUsername(fireFlow.getUsername());
        if (!userToAdd.isPresent()) {
            Role role = roleRepository.findByName("Admin");
            fireFlow.setUser(new User(fireFlow.getUsername(), role));
            fireFlow.setPassword(bCryptPasswordEncoder.encode(fireFlow.getPassword()));
            return new ResponseEntity<FireFlow>(fireFlowRepository.save(fireFlow), HttpStatus.OK);
        } else {
            return new ResponseEntity<FireFlow>(fireFlow, HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
