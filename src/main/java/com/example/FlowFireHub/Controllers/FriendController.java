package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.Friend;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.FriendRepository;
import com.example.FlowFireHub.Repositories.UserRepository;
import com.example.FlowFireHub.Utilities.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@CrossOrigin
@RestController
@RequestMapping(value = "/friend", path = "/friend")
public class FriendController {


    @Autowired
    UserRepository userRepository;
    @Autowired
    FriendRepository friendRepository;

    @PostMapping("/requestFriend/{id}")
    public ResponseEntity<User> requestFriend(@RequestBody User user, @PathVariable("id") Long id) {
        Optional<User> userEntity1 = userRepository.findById(user.getId());
        Optional<User> userEntity2 = userRepository.findById(id);
        if (userEntity1.isPresent() && userEntity2.isPresent()) {
            User user1 = userEntity1.get();
            User user2 = userEntity2.get();
            Friend.Key friendKey = new Friend.Key(user1.getId(), user2.getId());
            Friend friend = new Friend(friendKey, user1, user2);
            friendRepository.save(friend);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/acceptFriend/{id}")
    public ResponseEntity<User> acceptFriend(@RequestBody User user, @PathVariable("id") Long id) {
        Optional<User> userEntity1 = userRepository.findById(user.getId());
        Optional<User> userEntity2 = userRepository.findById(id);
        if (userEntity1.isPresent() && userEntity2.isPresent()) {
            User user1 = userEntity1.get();
            User user2 = userEntity2.get();
            friendRepository.acceptFriend(user1.getId(), user2.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getIncomming")
    public Iterable<UserType> getIncomming(@RequestBody User user) {
        Iterable<UserType> users = friendRepository.getIncomming(user.getId());
        return users;
    }

    @GetMapping("/getPending")
    public Iterable<UserType> getPending(@RequestBody User user) {
        Iterable<UserType> users = friendRepository.getPending(user.getId());
        return users;
    }

    @GetMapping("/getFriends/{id}")
    public Iterable<UserType> getFriends(@PathVariable("id") Long id) {
        Iterable<UserType> users = friendRepository.getFriends(id);
        return users;
    }

    @PostMapping("/declineFriend/{id}")
    public ResponseEntity<User> declineFriend(@RequestBody User user, @PathVariable("id") Long id) {
        Optional<User> userEntity1 = userRepository.findById(user.getId());
        Optional<User> userEntity2 = userRepository.findById(id);
        if (userEntity1.isPresent() && userEntity2.isPresent()) {
            User user1 = userEntity1.get();
            User user2 = userEntity2.get();
            friendRepository.declineFriend(user1.getId(), user2.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/deleteFriend/{id}")
    public ResponseEntity<User> deleteFriend(@RequestBody User user, @PathVariable("id") Long id) {
        Optional<User> userEntity1 = userRepository.findById(user.getId());
        Optional<User> userEntity2 = userRepository.findById(id);
        if (userEntity1.isPresent() && userEntity2.isPresent()) {
            User user1 = userEntity1.get();
            User user2 = userEntity2.get();
            friendRepository.deleteFriend(user1.getId(), user2.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}
