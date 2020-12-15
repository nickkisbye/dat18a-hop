package com.example.FlowFireHub.Controllers;

import com.example.FlowFireHub.Domains.ChatMessage;
import com.example.FlowFireHub.Domains.User;
import com.example.FlowFireHub.Respositories.ChatMessageRepository;
import com.example.FlowFireHub.Respositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ChatMessage testing(Model model) {
        ChatMessage cm = new ChatMessage();
        cm.setContent("this is a message");
        cm.setSender("john doe");
        cm.setType(ChatMessage.MessageType.CHAT);
        chatMessageRepository.save(cm);
        return cm;
    }


}