package org.example.learningprojectserver.controller;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.service.ChatGptService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.example.learningprojectserver.constants.ControllerConstants.ChatGpt.*;


@RestController
@RequestMapping(CHAT_BASE_PATH)
@RequiredArgsConstructor
public class ChatGptController {

    private final ChatGptService chatGptService;

    @PostMapping(WITH_MEMORY)
    public String sendMessage(@RequestParam String userId, @RequestParam String message) {
    return chatGptService.getResponseFromChatGptWithMemory(userId, message);
    }
    @PostMapping(SINGLE_RESPONSE)
    public String sendMessage(@RequestParam String message) {
        return chatGptService.getSingleResponseFromChatGpt(message);
    }


    @PostMapping(CLEAR_CONVERSATION)
    public void clearConversation(@RequestParam String userName) {
        chatGptService.clearConversation(userName);
    }

}