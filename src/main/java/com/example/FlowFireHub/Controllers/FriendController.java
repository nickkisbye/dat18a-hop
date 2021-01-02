package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.Friend;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.FriendRepository;
import com.example.FlowFireHub.Repositories.UserRepository;
import com.example.FlowFireHub.Services.JwtUserDetailsService;
import com.example.FlowFireHub.Utilities.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping(value = "/friend", path = "/friend")
public class FriendController {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @PostMapping("/requestFriend/{id}")
    public ResponseEntity<User> requestFriend(HttpServletRequest request, @PathVariable("id") Long id) throws ServletException {
        String token = jwtUserDetailsService.splitToken(request);
        User userEntity1 = jwtUserDetailsService.authenticateToken(token);
        Optional<User> userEntity2 = userRepository.findById(id);
        if (userEntity1 != null && userEntity2.isPresent()) {
            User user2 = userEntity2.get();
            Friend.Key friendKey = new Friend.Key(userEntity1.getId(), user2.getId());
            Friend friend = new Friend(friendKey, userEntity1, user2);
            friendRepository.save(friend);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/acceptFriend/{id}")
    public ResponseEntity<User> acceptFriend(HttpServletRequest request, @PathVariable("id") Long id) throws ServletException {
        String token = jwtUserDetailsService.splitToken(request);
        User userEntity1 = jwtUserDetailsService.authenticateToken(token);
        Optional<User> userEntity2 = userRepository.findById(id);
        if (userEntity1 != null && userEntity2.isPresent()) {
            User user2 = userEntity2.get();
            friendRepository.acceptFriend(userEntity1.getId(), user2.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getIncomming")
    public Iterable<UserType> getIncomming(HttpServletRequest request) throws ServletException {
        String token = jwtUserDetailsService.splitToken(request);
        User user = jwtUserDetailsService.authenticateToken(token);
        Iterable<UserType> users = friendRepository.getIncomming(user.getId());
        return users;
    }

    @GetMapping("/getPending")
    public Iterable<UserType> getPending(HttpServletRequest request) throws ServletException {
        String token = jwtUserDetailsService.splitToken(request);
        User user = jwtUserDetailsService.authenticateToken(token);
        Iterable<UserType> users = friendRepository.getPending(user.getId());
        return users;
    }

    @GetMapping("/getFriends/{id}")
    public Iterable<UserType> getFriends(@PathVariable("id") Long id) {
        Iterable<UserType> users = friendRepository.getFriends(id);
        return users;
    }

    @PostMapping("/declineFriend/{id}")
    public ResponseEntity<User> declineFriend(HttpServletRequest request, @PathVariable("id") Long id) throws ServletException {
        String token = jwtUserDetailsService.splitToken(request);
        User userEntity1 = jwtUserDetailsService.authenticateToken(token);
        Optional<User> userEntity2 = userRepository.findById(id);
        if (userEntity1 != null && userEntity2.isPresent()) {
            User user2 = userEntity2.get();
            friendRepository.declineFriend(userEntity1.getId(), user2.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteFriend/{id}")
    public ResponseEntity<User> deleteFriend(HttpServletRequest request, @PathVariable("id") Long id) throws ServletException {
        String token = jwtUserDetailsService.splitToken(request);
        User userEntity1 = jwtUserDetailsService.authenticateToken(token);
        Optional<User> userEntity2 = userRepository.findById(id);
        if (userEntity1 != null && userEntity2.isPresent()) {
            User user2 = userEntity2.get();
            friendRepository.deleteFriend(userEntity1.getId(), user2.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
