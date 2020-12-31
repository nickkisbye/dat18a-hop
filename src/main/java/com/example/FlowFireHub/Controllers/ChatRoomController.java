package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.ChatMessage;
import com.example.FlowFireHub.Domains.ChatRoom;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.ChatMessageRepository;
import com.example.FlowFireHub.Repositories.ChatRoomRepository;
import com.example.FlowFireHub.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping(value = "/chatroom", path = "/chatroom")
public class ChatRoomController {

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

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

    @GetMapping("/getAll")
    public ResponseEntity getAllRooms() {
        List<ChatRoom> rooms = chatRoomRepository.findAll();
        return new ResponseEntity(rooms, HttpStatus.OK);
    }

    @GetMapping("/getAllMessages/{id}")
    public ResponseEntity getAllMessages(@PathVariable("id") Long id) {
        List<ChatMessage> messages = chatMessageRepository.getMessageByRoomId(id);
        return new ResponseEntity(messages, HttpStatus.OK);
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
