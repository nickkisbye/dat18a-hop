package com.example.FlowFireHub.Utilities;

import com.example.FlowFireHub.Domains.ChatRoom;
import com.example.FlowFireHub.Domains.FireFlow;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.ChatRoomRepository;
import com.example.FlowFireHub.Respositories.FireFlowRepository;
import com.example.FlowFireHub.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//@Profile({"dev", "test"})
@Component
public class DummyDataLoader implements CommandLineRunner {

    @Autowired
    UserRepository userRepository;

    @Autowired
    FireFlowRepository fireFlowRepository;

    @Autowired
    ChatRoomRepository chatRoomRepository;

    @Override
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("Rasmus");
        FireFlow fireFlow = new FireFlow();
        fireFlow.setPassword("$2a$10$uPvcrThCwgBdrfgJ.hGmEeIwMxmTNcViKb4oZ./t9HrADh//rsacS");
        fireFlow.setUsername("Rasmus");
        fireFlow.setUser(user);
        fireFlowRepository.save(fireFlow);

        ChatRoom room = new ChatRoom("public", false);
        chatRoomRepository.save(room);
    }
}
