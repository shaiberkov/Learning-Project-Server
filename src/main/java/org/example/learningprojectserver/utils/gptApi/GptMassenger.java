package org.example.learningprojectserver.utils.gptApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GptMassenger {



    private static Map<String, List<ChatMessage>> userConversations = new HashMap<>();

    public static String getResponseFromChatGpt(String userName, String userMessage) {
        String responseText = null;
        try {
            userConversations.putIfAbsent(userName, new ArrayList<>());
            List<ChatMessage> conversation = userConversations.get(userName);

            conversation.add(new ChatMessage("user", userMessage));

            ChatGptRequest chatGptRequest = new ChatGptRequest("gpt-4o", conversation);

            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer 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");

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(chatGptRequest), headers);

            ResponseEntity<String> response = restTemplate.exchange("https://api.openai.com/v1/chat/completions", HttpMethod.POST, entity, String.class);

            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray choices = jsonObject.getJSONArray("choices");
                JSONObject firstChoice = choices.getJSONObject(0);
                JSONObject message = firstChoice.getJSONObject("message");

                String modelResponse = message.getString("content");
                conversation.add(new ChatMessage("assistant", modelResponse));
                responseText = modelResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseText;
    }


    public static void clearConversation(String userId) {
        if (userConversations.containsKey(userId)) {
            userConversations.get(userId).clear();
        }
    }




}
