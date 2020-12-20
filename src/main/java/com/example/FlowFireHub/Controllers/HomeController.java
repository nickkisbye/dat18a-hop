package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.ChatMessage;
import com.example.FlowFireHub.Domains.FireFlow;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.ChatMessageRepository;
import com.example.FlowFireHub.Respositories.FireFlowRepository;
import com.example.FlowFireHub.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatMessageRepository chatMessageRepository;

    @Autowired
    FireFlowRepository fireFlowRepository;


    @GetMapping("/chat")
    public String chat(Model model) {
        return "chat";
    }

    @GetMapping("/steam")
    public String steam(Model model) {
        return "steam";
    }

//    @GetMapping("/add")
//    public User add(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
//        User testuser = new User("rasmus", "hoeberg");
//        userRepository.save(testuser);
//        model.addAttribute("name", name);
//        return testuser;
////        return "greeting";
//    }

    @GetMapping("/add")
    public User add(@RequestParam(name = "name", required = false, defaultValue = "World") String name, Model model) {
        return new User();
    }

    @GetMapping("/testing")
    public ResponseEntity<User> testing() {
//        ChatMessage cm = new ChatMessage();
//        cm.setContent("this is a message");
//        cm.setSender("john doe");
//        cm.setType(ChatMessage.MessageType.CHAT);
//        chatMessageRepository.save(cm);
//        return cm;

        User user = new User();
        user.setUsername("Rasmus");
//////
////        user.setFireFlow(fireFlow);
////
//        userRepository.save(user);

        FireFlow fireFlow = new FireFlow();
        fireFlow.setPassword("$2a$10$uPvcrThCwgBdrfgJ.hGmEeIwMxmTNcViKb4oZ./t9HrADh//rsacS");
        fireFlow.setUser(user);
        fireFlowRepository.save(fireFlow);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}