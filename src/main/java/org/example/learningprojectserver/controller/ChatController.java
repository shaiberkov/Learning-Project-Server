package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.utils.gptApi.GptMassenger;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    // מתודה לשליחת הודעה ולקבל תשובה
    @PostMapping("/send-message")
    public String sendMessage(@RequestParam String userName, @RequestParam String message) {
        // קבלת תשובה מה-ChatService
        String response = GptMassenger.getResponseFromChatGpt(userName, message);
        System.out.println(response);
        if (response != null) {
            return response;
        } else {
            return "Failed to get response from ChatGPT";
        }
    }

    // מתודה לניקוי שיחה של משתמש
    @PostMapping("/clear-conversation")
    public void clearConversation(@RequestParam String userName) {
        GptMassenger.clearConversation(userName);
    }
}