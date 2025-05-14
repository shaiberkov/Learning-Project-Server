package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.service.ChatGptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Learning-App/Chat")
public class ChatGptController {

    private final ChatGptService chatGptService;
@Autowired
    public ChatGptController(ChatGptService chatGptService) {
        this.chatGptService = chatGptService;
    }

    @PostMapping("/get-response-from-chatGpt-with-memory")
    public String sendMessage(@RequestParam String userId, @RequestParam String message) {
    return chatGptService.getResponseFromChatGptWithMemory(userId, message);
    }
    @PostMapping("/get-single-response-from-chatGpt")
    public String sendMessage(@RequestParam String message) {
        return chatGptService.getSingleResponseFromChatGpt(message);
    }


    @PostMapping("/clear-conversation")
    public void clearConversation(@RequestParam String userName) {
        chatGptService.clearConversation(userName);
    }

}