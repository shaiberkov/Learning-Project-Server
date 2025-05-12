package org.example.learningprojectserver.controller;

import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Learning-App/Message")
public class MessageController {

private final MessageService messageService;


    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/recipient-types")
    public BasicResponse getRecipientTypes(@RequestParam String userId) {
    return messageService.getRecipientTypes(userId);
    }


    @PostMapping("/send-message")
    public BasicResponse sendMessage(
            @RequestParam String senderId,
            @RequestParam String recipientType,
            @RequestParam String recipientValue,
            @RequestParam String title,
            @RequestParam String content
    ) {
        return messageService.sendMessage(senderId, recipientType, recipientValue, title, content);
    }

    @GetMapping("/get-all-recived-messages")
    public BasicResponse getAllRecivedMessages(@RequestParam String userId) {
        return messageService.getAllRecivedMessages(userId);
    }


    }

