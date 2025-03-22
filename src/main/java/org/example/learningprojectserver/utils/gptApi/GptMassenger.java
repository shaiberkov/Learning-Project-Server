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

//
//    public static String getResponseFromChatGpt() {
//        try {
//            List<ChatMessage> conversation = new ArrayList<>();
//            conversation.add(new ChatMessage("user", "what is the capital of Israel?"));
//            ChatGptRequest chatGptRequest = new ChatGptRequest("gpt-4o", conversation);
//            HttpHeaders headers = new HttpHeaders();
//            headers.set("Content-Type", "application/json");
//            headers.set("Authorization", "Bearer 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");
//            RestTemplate restTemplate = new RestTemplate();
//            HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(chatGptRequest), headers);
//            ResponseEntity<String> response = restTemplate.exchange("https://api.openai.com/v1/chat/completions", HttpMethod.POST, entity, String.class);
//            if (response != null) {
//                if (response.getStatusCode() == HttpStatus.OK) {
//                    JSONObject jsonObject = new JSONObject(response.getBody());
//                    JSONArray choices = jsonObject.getJSONArray("choices");
//                    JSONObject firstChoice = choices.getJSONObject(0);
//                    JSONObject message = firstChoice.getJSONObject("message");
//                    System.out.println(message.getString("content"));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null; // צריך להוסיף את ההחזרה של התשובה
//    }


    private static Map<String, List<ChatMessage>> userConversations = new HashMap<>();

    // מתודה לקבלת תשובה מהמודל
    public static String getResponseFromChatGpt(String userName, String userMessage) {
        String responseText = null;
        try {
            // אם אין שיחה קיימת עבור המשתמש, צור שיחה חדשה
            userConversations.putIfAbsent(userName, new ArrayList<>());
            List<ChatMessage> conversation = userConversations.get(userName);

            // הוסף את הודעת המשתמש לשיחה
            conversation.add(new ChatMessage("user", userMessage));

            // יצירת בקשה ל-ChatGPT עם כל השיחה של המשתמש
            ChatGptRequest chatGptRequest = new ChatGptRequest("gpt-4o", conversation);

            // הגדרת כותרות
            HttpHeaders headers = new HttpHeaders();
            headers.set("Content-Type", "application/json");
            headers.set("Authorization", "Bearer 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");

            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(chatGptRequest), headers);

            // שלח את הבקשה ל-API של OpenAI
            ResponseEntity<String> response = restTemplate.exchange("https://api.openai.com/v1/chat/completions", HttpMethod.POST, entity, String.class);

            // אם התגובה הצליחה
            if (response != null && response.getStatusCode() == HttpStatus.OK) {
                JSONObject jsonObject = new JSONObject(response.getBody());
                JSONArray choices = jsonObject.getJSONArray("choices");
                JSONObject firstChoice = choices.getJSONObject(0);
                JSONObject message = firstChoice.getJSONObject("message");

                // הוסף את תשובת המודל לשיחה
                String modelResponse = message.getString("content");
                conversation.add(new ChatMessage("assistant", modelResponse));

                // החזר את התשובה
                responseText = modelResponse;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return responseText;
    }

    // מתודה לניקוי שיחה עבור משתמש ספציפי
    public static void clearConversation(String userId) {
        // אם קיימת שיחה עבור המשתמש, מנקים אותה
        if (userConversations.containsKey(userId)) {
            userConversations.get(userId).clear();
        }
    }




}
