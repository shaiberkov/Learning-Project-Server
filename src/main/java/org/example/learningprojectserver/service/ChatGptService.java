package org.example.learningprojectserver.service;


import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.utils.gptApi.ChatGptRequest;
import org.example.learningprojectserver.utils.gptApi.ChatMessage;
import org.example.learningprojectserver.utils.gptApi.GptMassenger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class ChatGptService {

    @Autowired
    private final GptMassenger gptMassenger;

    private static Map<String, List<ChatMessage>> userConversations = new HashMap<>();

    public String getResponseFromChatGptWithMemory(String userId, String userMessage) {
        try {
            userConversations.putIfAbsent(userId, new ArrayList<>());
            List<ChatMessage> conversation = userConversations.get(userId);
            conversation.add(new ChatMessage("user", userMessage));

            ChatGptRequest chatGptRequest = new ChatGptRequest("gpt-4o", conversation);
            String response = gptMassenger.sendChatGptRequest(chatGptRequest);

            if (response != null) {
                conversation.add(new ChatMessage("assistant", response));
                return response;
            } else {
                return "אירעה שגיאה במהלך קבלת התשובה. אנא נסה שוב.";
            }

        } catch (Exception e) {
            return "אירעה שגיאה במהלך קבלת התשובה. אנא נסה שוב.";
        }
    }

    public String getSingleResponseFromChatGpt(String userMessage) {
        try {
            List<ChatMessage> messages = List.of(new ChatMessage("user", userMessage));
            ChatGptRequest chatGptRequest = new ChatGptRequest("gpt-4o", messages);
            String response = gptMassenger.sendChatGptRequest(chatGptRequest);

            return (response != null) ? response : "אירעה שגיאה במהלך קבלת התשובה. אנא נסה שוב.";

        } catch (Exception e) {
            return "אירעה שגיאה במהלך קבלת התשובה. אנא נסה שוב.";
        }
    }

    public void clearConversation(String userId) {
        if (userConversations.containsKey(userId)) {
            userConversations.get(userId).clear();
        }
    }


    @Async
    public CompletableFuture<String> initializeConversationWithJob(String userId, String jobMessage) {
        try {
            userConversations.put(userId, new ArrayList<>());
            List<ChatMessage> conversation = userConversations.get(userId);
            conversation.add(new ChatMessage("user", jobMessage));

            ChatGptRequest chatGptRequest = new ChatGptRequest("gpt-4o", conversation);
            String response = gptMassenger.sendChatGptRequest(chatGptRequest);

            if (response != null) {
                conversation.add(new ChatMessage("assistant", response));
                return CompletableFuture.completedFuture(response);
            } else {
                return CompletableFuture.completedFuture("אירעה שגיאה במהלך אתחול השיחה.");
            }
        } catch (Exception e) {
            return CompletableFuture.completedFuture("אירעה שגיאה במהלך אתחול השיחה.");
        }
    }

}
