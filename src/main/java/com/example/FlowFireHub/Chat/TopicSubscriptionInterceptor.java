package com.example.FlowFireHub.Chat;

import com.example.FlowFireHub.Auth.AuthToken;
import com.example.FlowFireHub.Auth.IValues;
import com.example.FlowFireHub.Auth.JwtAuthFilter;
import com.example.FlowFireHub.Domains.ChatMessage;
import com.example.FlowFireHub.Domains.ChatRoom;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.ChatMessageRepository;
import com.example.FlowFireHub.Respositories.ChatRoomRepository;
import com.example.FlowFireHub.Respositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Component
public class TopicSubscriptionInterceptor implements ChannelInterceptor {

    private static UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        TopicSubscriptionInterceptor.userRepository = userRepository;
    }

    private static ChatRoomRepository chatRoomRepository;

    @Autowired
    public void setChatRoomRepository(ChatRoomRepository chatRoomRepository) {
        TopicSubscriptionInterceptor.chatRoomRepository = chatRoomRepository;
    }

    private static ChatMessageRepository chatMessageRepository;

    @Autowired
    public void setChatMessageRepository(ChatMessageRepository chatMessageRepository) {
        TopicSubscriptionInterceptor.chatMessageRepository = chatMessageRepository;
    }


    public User authenticateToken(String token) throws ServletException {
        User result = null;

        SecurityContext context = SecurityContextHolder.getContext();
        Jws<Claims> claims = Jwts.parser().requireIssuer(IValues.ISSUER).setSigningKey(IValues.SECRET_KEY).parseClaimsJws(token);
        String user = (String) claims.getBody().get("usr");
        String roles = (String) claims.getBody().get("rol");
        List<GrantedAuthority> authority = new ArrayList<GrantedAuthority>();
        for (String role : roles.split(","))
            authority.add(new SimpleGrantedAuthority(role));

        AuthToken authenticationTkn = new AuthToken(user, null, authority);
        result = userRepository.findByUsername(user).get();
        context.setAuthentication(authenticationTkn);

        return result;
    }

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
        System.out.println(headerAccessor.getCommand());

        String token = null;
        if (headerAccessor.containsNativeHeader("Bearer")) {
            token = headerAccessor.getNativeHeader("Bearer").get(0);
            System.out.println(token);
        }

        if (token != null) {
            userRepository.count();
            try {
                User user = authenticateToken(token);
                System.out.println(user.getUsername());
                // succefully authenticated user
                // now check if we are allowed to subscribe to room
                if (StompCommand.SUBSCRIBE.equals(headerAccessor.getCommand())) {
                    if(headerAccessor.containsNativeHeader("room")) {
                        Long roomId = Long.parseLong(headerAccessor.getNativeHeader("room").get(0));

                        ChatRoom chatRoom = chatRoomRepository.findById(roomId).get();
                        System.out.println(headerAccessor.getDestination());
                        System.out.println(chatRoom.getName());
                        if(!chatRoom.isPrivate() || chatRoom.getUsers().contains(user)) {
                            System.out.println("hej");
                        }
                        else {
                            throw new IllegalArgumentException("No permission in this chat room");
                        }
                    }
                }
            } catch (ServletException e) {
                System.out.println(e);
            }
        } else {
            // no token available
        }

        return message;
    }

}
