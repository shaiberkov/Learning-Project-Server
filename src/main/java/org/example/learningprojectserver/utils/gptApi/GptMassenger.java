package org.example.learningprojectserver.utils.gptApi;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.env.Environment;


import java.io.IOException;


@Component
public class GptMassenger {
@Autowired
public  Environment env;

    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    public String apiKey(){
        return env.getProperty("GPT_TOKEN");
    }

    public  HttpHeaders createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Authorization",apiKey());
        return headers;
    }

    public String sendChatGptRequest(ChatGptRequest request) throws IOException {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = createHeaders();
        HttpEntity<String> entity = new HttpEntity<>(new ObjectMapper().writeValueAsString(request), headers);

        ResponseEntity<String> response = restTemplate.exchange(API_URL, HttpMethod.POST, entity, String.class);
        System.out.println(response.getBody());
        if (response != null && response.getStatusCode() == HttpStatus.OK) {
            JSONObject jsonObject = new JSONObject(response.getBody());
            JSONArray choices = jsonObject.getJSONArray("choices");
            JSONObject firstChoice = choices.getJSONObject(0);
            JSONObject message = firstChoice.getJSONObject("message");
            return message.getString("content");
        }
        return null;
    }

    }





