package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.ChatRoom;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.ChatRoomRepository;
import com.example.FlowFireHub.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/chatroom", path = "/chatroom")
public class ChatRoomController {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/subscribe/{id}")
    public ResponseEntity subscribeToRoom(@RequestHeader long userId, @PathVariable("id") long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        User user = userRepository.findById(userId);
        if(room != null && user != null) {
            room.addUser(user);
            chatRoomRepository.save(room);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/unsubscribe/{id}")
    public ResponseEntity unsubscribeToRoom(@RequestHeader long userId, @PathVariable("id") long roomId) {
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        User user = userRepository.findById(userId);

        if(room != null && user != null) {
            room.removeUser(user);
            chatRoomRepository.save(room);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }
}
