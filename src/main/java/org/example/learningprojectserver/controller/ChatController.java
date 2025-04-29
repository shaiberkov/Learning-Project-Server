package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.utils.gptApi.GptMassenger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Learning-App/Chat")
public class ChatController {

    @PostMapping("/send-message")
    public String sendMessage(@RequestParam String userName, @RequestParam String message) {
        String response = GptMassenger.getResponseFromChatGpt(userName, message);
        if (response != null) {
            return response;
        } else {
            return "Failed to get response from ChatGPT";
        }
    }

    @PostMapping("/clear-conversation")
    public void clearConversation(@RequestParam String userName) {
        GptMassenger.clearConversation(userName);
    }
}