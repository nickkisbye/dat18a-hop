package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Auth.JwtAuthFilter;
import com.example.FlowFireHub.Domains.ChatMessage;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Repositories.ChatMessageRepository;
import com.example.FlowFireHub.Repositories.ChatRoomRepository;
import com.example.FlowFireHub.Services.JwtUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import java.time.LocalDateTime;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        chatMessageRepository.save(chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage,
                               SimpMessageHeaderAccessor headerAccessor) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @MessageMapping("/chat.sendMessage/{room}")
    @SendTo("/topic/{room}")
    public ChatMessage sendToRoom(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
        long roomId = headerAccessor.containsNativeHeader("room") ? Long.parseLong(headerAccessor.getNativeHeader("room").get(0)) : -1;
        chatMessage.setChatRoom(chatRoomRepository.getOne(roomId));
        chatMessage.setTimeStamp(LocalDateTime.now());
        try {
            User user = jwtUserDetailsService.authenticateToken(headerAccessor.getFirstNativeHeader("Bearer"));
            chatMessage.setUser(user);
            chatMessageRepository.save(chatMessage);
        } catch(ServletException e) {
            System.out.println(e);
        }
        return chatMessage;
    }
}
