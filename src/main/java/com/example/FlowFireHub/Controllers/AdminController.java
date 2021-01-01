package com.example.FlowFireHub.Controllers;


import com.example.FlowFireHub.Domains.FlowFire;
import com.example.FlowFireHub.Domains.Steam;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.FlowFireRepository;
import com.example.FlowFireHub.Repositories.RoleRepository;
import com.example.FlowFireHub.Repositories.SteamRepository;
import com.example.FlowFireHub.Repositories.UserRepository;
import com.example.FlowFireHub.Utilities.SteamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value = "/admin", path = "/admin")
public class AdminController {

    SteamRepository steamRepository;
    @Autowired
    SteamManager steamManager;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    FlowFireRepository flowFireRepository;


    @DeleteMapping("/steam/deleteUser/{id}")
    public ResponseEntity<Steam> deleteSteamUser(@PathVariable("id") Long id) {
        try {
            steamRepository.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
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
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/flowfire/deleteUser/{id}")
    public ResponseEntity<FlowFire> deleteFlowFireUser(@PathVariable("id") Long id) {
        try {
            flowFireRepository.deleteUserById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
