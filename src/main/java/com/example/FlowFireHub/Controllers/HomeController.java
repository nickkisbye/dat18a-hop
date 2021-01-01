package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.FlowFire;
import com.example.FlowFireHub.Domains.Role;
import com.example.FlowFireHub.Domains.Steam;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.*;
import com.example.FlowFireHub.Utilities.SteamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    ChatMessageRepository chatMessageRepository;
    @Autowired
    FlowFireRepository flowFireRepository;
    @Autowired
    SteamRepository steamRepository;
    @Autowired
    SteamManager steamManager;
    @Autowired
    RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();


    @GetMapping("/steam/login")
    public ResponseEntity<Steam> steamLogin(HttpServletRequest request) {
        String steam_openid = request.getParameter("openid.identity");
        String steamData = steamManager.getSteamData(steam_openid);
        String steamid = steamManager.getKey(steamData, "steamid");
        String username = steamManager.getKey(steamData, "personaname");
        Optional<Steam> addSteamUser = steamRepository.findByUsernameOrSteamId(username, steamid);
        if(!addSteamUser.isPresent()) {
            Steam steamuser = new Steam();
            steamuser.setUsername(username);
            steamuser.setSteamid(steamid);
            Role role = roleRepository.findByName("User");
            steamuser.setUser(new User(username, role));
            return new ResponseEntity<Steam>(steamRepository.save(steamuser), HttpStatus.OK);
        } else {
            Steam steam = addSteamUser.get();
            return new ResponseEntity<Steam>(steam, HttpStatus.NOT_ACCEPTABLE);
        }
    }

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

}