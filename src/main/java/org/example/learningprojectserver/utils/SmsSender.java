package org.example.learningprojectserver.utils;


    import org.json.JSONArray;
import org.json.JSONObject;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.core.env.Environment;
    import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
    import org.springframework.stereotype.Component;
    import org.springframework.web.client.RestTemplate;

    import java.util.ArrayList;
    import java.util.List;
    import java.util.concurrent.ExecutorService;
    import java.util.concurrent.Executors;

@Component
public class SmsSender {
    private static final ExecutorService emailExecutor = Executors.newCachedThreadPool();
@Autowired
private Environment env;


    public void sendSms(String text, List<String> phones) {
        emailExecutor.execute(() -> sendSms1(text,phones));
    }
        public boolean sendSms1 (String text, List<String> phones) {
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
            httpHeaders.add("Authorization", "Basic " + env.getProperty("SMS_TOKEN"));
            RestTemplate restTemplate = new RestTemplate();
            HttpEntity<?> httpEntity = new HttpEntity<Object>(jsonObject.toString(), httpHeaders);
            ResponseEntity<String> response = restTemplate.exchange("https://capi.inforu.co.il/api/v2/SMS/SendSms", HttpMethod.POST, httpEntity, String.class);

            return true;
        }
    }

