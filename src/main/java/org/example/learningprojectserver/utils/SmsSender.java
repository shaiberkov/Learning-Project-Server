package org.example.learningprojectserver.utils;


    import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;


public class SmsSender {
    private static final ExecutorService emailExecutor = Executors.newCachedThreadPool();




    public static void sendSms(String text, List<String> phones) {
        emailExecutor.execute(() -> sendSms1(text,phones));
    }
        public static boolean sendSms1 (String text, List<String> phones) {
            String token = "ZmVlZGJhY2sxOnlheGZxMzJpd2lnYW15bHBvMG9tNHVycjU";
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("Message", text);
            JSONArray recipients = new JSONArray();

            for (String phone : phones) {
                JSONObject recipient = new JSONObject();
                recipient.put("Phone", phone);
                recipients.put(recipient);
            }

            jsonObject.put("Recipients", recipients);
            JSONObject settingsJson = new JSONObject();
            settingsJson.put("Sender", "LearningApp");
            jsonObject.put("Settings", settingsJson);
            System.out.println(jsonObject);
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Content-Type","application/json");
            httpHeaders.add("Authorization", "Basic " + token);
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<?> httpEntity = new HttpEntity<Object>(jsonObject.toString(), httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange("https://capi.inforu.co.il/api/v2/SMS/SendSms", HttpMethod.POST, httpEntity, String.class);

            return true;
        }
    }

