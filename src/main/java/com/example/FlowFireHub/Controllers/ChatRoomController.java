package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.ChatMessage;
import com.example.FlowFireHub.Domains.ChatRoom;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.ChatMessageRepository;
import com.example.FlowFireHub.Repositories.ChatRoomRepository;
import com.example.FlowFireHub.Repositories.UserRepository;
import com.example.FlowFireHub.Services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/chatroom", path = "/chatroom")
public class ChatRoomController {

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/subscribe/{id}")
    public ResponseEntity subscribeToRoom(HttpServletRequest request, @PathVariable("id") long roomId) throws ServletException {
        String token = jwtUserDetailsService.splitToken(request);
        User user = jwtUserDetailsService.authenticateToken(token);
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        if (room != null && user != null) {
            room.addUser(user);
            chatRoomRepository.save(room);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/unsubscribe/{id}")
    public ResponseEntity unsubscribeToRoom(HttpServletRequest request, @PathVariable("id") long roomId) throws ServletException {
        String token = jwtUserDetailsService.splitToken(request);
        User user = jwtUserDetailsService.authenticateToken(token);
        ChatRoom room = chatRoomRepository.findById(roomId).get();
        if (room != null && user != null) {
            room.removeUser(user);
            chatRoomRepository.save(room);
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/privatechat/{userid}")
    public ResponseEntity createPrivateChatRoom(HttpServletRequest request, @PathVariable("userid") long userId) throws ServletException {
        String token = jwtUserDetailsService.splitToken(request);
        User user1 = jwtUserDetailsService.authenticateToken(token);
        User user2 = userRepository.findById(userId);
        ChatRoom room = new ChatRoom();
        room.setPrivate(true);
        room.setName(user1.getUsername() + ", " + user2.getUsername());

        if(room != null && user1 != null && user2 != null) {
            room.addUser(user1);
            room.addUser(user2);
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
}
