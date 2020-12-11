package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.Friend;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = "/friend", path = "/friend")
public class FriendController {


    @Autowired
    UserRepository userRepository;

    @PostMapping("/saveFriend")
    public ResponseEntity<Friend> testfriend(@RequestBody Friend friend) {
        User user = userRepository.findById(Long.parseLong(friend.getUserid()));
        User user2 = userRepository.findById(Long.parseLong(friend.getFriendid()));
        user.getFriends().add(user2);
        userRepository.save(user);
        return new ResponseEntity<Friend>(HttpStatus.OK);
    }

    @GetMapping("/getById/{id}")
    public List<User> getUserById(@PathVariable("id") Long id) {
        System.out.println("test");
        List<User> user = userRepository.findByFriends_id(id);
        System.out.println("ads");
        //List<User> user = userRepository.findFriendsById(id);
        return user;
    }

    @PostMapping("/deleteUser")
    public ResponseEntity<Friend> deleteFriend(@RequestBody Friend friend) {
        try {
            User user = userRepository.findById(Long.parseLong(friend.getUserid()));
            User user2 = userRepository.findById(Long.parseLong(friend.getFriendid()));
            user.getFriends().remove(user2);
            userRepository.save(user);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getAll")
    public Iterable<User> getAllUsers() {
        Iterable<User> users = userRepository.findAll();
        return users;
    }

}
