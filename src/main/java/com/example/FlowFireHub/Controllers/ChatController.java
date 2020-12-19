package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Auth.JwtAuthFilter;
import com.example.FlowFireHub.Domains.ChatMessage;
import com.example.FlowFireHub.Respositories.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

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

//    @MessageMapping("/chat.sendMessage/{room}")
//    @SendTo("/topic/{room}")
//    public ChatMessage sendToRoom(@Payload ChatMessage chatMessage)
//    {
//        return chatMessage;
//    }

//    @MessageMapping("/chat.sendMessage/{room}/{token}")
    @MessageMapping("/chat.sendMessage/{room}")
    @SendTo("/topic/{room}")
    public ChatMessage sendToRoom(@Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor)
    {
        System.out.println(headerAccessor.getMessageHeaders());
//        jwtAuthFilter.authenticateToken(headerAccessor.getMessageHeaders());
        return chatMessage;
    }
}
