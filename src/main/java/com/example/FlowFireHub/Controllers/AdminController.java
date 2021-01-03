package com.example.FlowFireHub.Controllers;


import com.example.FlowFireHub.Domains.ChatRoom;
import com.example.FlowFireHub.Domains.FlowFire;
import com.example.FlowFireHub.Domains.Steam;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.*;
import com.example.FlowFireHub.Utilities.SteamManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping(value = "/admin", path = "/admin")
public class AdminController {

    private SteamRepository steamRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FlowFireRepository flowFireRepository;
    @Autowired
    private ChatMessageRepository chatMessageRepository;
    @Autowired
    private ChatRoomRepository chatRoomRepository;

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

    @DeleteMapping("/deleteChatMessage/{id}")
    public ResponseEntity deleteChatMessage(@PathVariable("id") long id) {
        chatMessageRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/deleteChatRoom/{id}")
    public ResponseEntity deleteChatRoom(@PathVariable("id") long id) {
        chatRoomRepository.deleteById(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @PutMapping("/updateChatRoom/{id}")
    public ResponseEntity updateChatRoom(@PathVariable("id") long id, @RequestBody ChatRoom chatRoom) {
        Optional<ChatRoom> chatRoomToUpdate = chatRoomRepository.findById(id);
        if(chatRoomToUpdate.isPresent()) {
            ChatRoom newChatRoom = chatRoomToUpdate.get();
            newChatRoom.setName(chatRoom.getName());
            return new ResponseEntity<>(chatRoomRepository.save(newChatRoom), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/createChatRoom")
    public ResponseEntity createChatRoom(@RequestBody ChatRoom chatRoom) {
        return new ResponseEntity<>(chatRoomRepository.save(chatRoom), HttpStatus.OK);
    }

}
