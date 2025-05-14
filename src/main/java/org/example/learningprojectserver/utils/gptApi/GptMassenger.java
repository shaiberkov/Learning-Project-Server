package org.example.learningprojectserver.utils.gptApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class GptMassenger {



//    private static Map<String, List<ChatMessage>> userConversations = new HashMap<>();
//
//    public static String getResponseFromChatGpt(String userName, String userMessage) {
//        String responseText = null;
//        try {
//            userConversations.putIfAbsent(userName, new ArrayList<>());
//            List<ChatMessage> conversation = userConversations.get(userName);
//
//            conversation.add(new ChatMessage("user", userMessage));
//
//            ChatGptRequest chatGptRequest = new ChatGptRequest("gpt-4o", conversation);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Content-Type", "application/json");
//            headers.set("Authorization", "Bearer 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");
//
//            RestTemplate restTemplate = new RestTemplate();
//            HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(chatGptRequest), headers);
//
//            ResponseEntity<String> response = restTemplate.exchange("https://api.openai.com/v1/chat/completions", HttpMethod.POST, entity, String.class);
//
//            if (response != null && response.getStatusCode() == HttpStatus.OK) {
//                JSONObject jsonObject = new JSONObject(response.getBody());
//                JSONArray choices = jsonObject.getJSONArray("choices");
//                JSONObject firstChoice = choices.getJSONObject(0);
//                JSONObject message = firstChoice.getJSONObject("message");
//
//                String modelResponse = message.getString("content");
//                conversation.add(new ChatMessage("assistant", modelResponse));
//                responseText = modelResponse;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return responseText;
//    }
//
//
//
//    public static String getSingleResponseFromChatGpt(String userMessage) {
//        String responseText = null;
//        try {
//            // צור את ההודעה של המשתמש ללא היסטוריה
//            List<ChatMessage> messages = List.of(new ChatMessage("user", userMessage));
//
//            // בקשת GPT רק עם ההודעה החדשה
//            ChatGptRequest chatGptRequest = new ChatGptRequest("gpt-4o", messages);
//
//            // הכנת הכותרות (Headers)
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Content-Type", "application/json");
//            headers.set("Authorization", "Bearer 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");
//
//            // שליחת הבקשה
//            RestTemplate restTemplate = new RestTemplate();
//            HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(chatGptRequest), headers);
//
//            ResponseEntity<String> response = restTemplate.exchange(
//                    "https://api.openai.com/v1/chat/completions",
//                    HttpMethod.POST,
//                    entity,
//                    String.class
//            );
//
//            // עיבוד התגובה
//            if (response != null && response.getStatusCode() == HttpStatus.OK) {
//                JSONObject jsonObject = new JSONObject(response.getBody());
//                JSONArray choices = jsonObject.getJSONArray("choices");
//                JSONObject firstChoice = choices.getJSONObject(0);
//                JSONObject message = firstChoice.getJSONObject("message");
//
//                responseText = message.getString("content");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return responseText;
//    }



    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String API_KEY = "Bearer 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";

    public static HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization", API_KEY);
        return headers;
    }

    public static String sendChatGptRequest(ChatGptRequest request) throws IOException, JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(request), headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject firstChoice = choices.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");
            return message.getString("content");
        }
        return null;
    }
//    public static void clearConversation(String userId) {
//        if (userConversations.containsKey(userId)) {
//            userConversations.get(userId).clear();
//        }
    }





