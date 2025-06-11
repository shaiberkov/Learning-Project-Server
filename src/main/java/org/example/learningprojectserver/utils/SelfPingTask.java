package org.example.learningprojectserver.utils;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.*;

@Component
public class SelfPingTask {

    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private static final String PING_URL = "https://learning-project-server.onrender.com/ping";

    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {
        System.out.println("🚀 Server is ready! Starting self-ping task...");
        scheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("🔁 Sending ping to: " + PING_URL);
                HttpURLConnection connection = (HttpURLConnection) new URL(PING_URL).openConnection();
                connection.setRequestMethod("GET");
                int responseCode = connection.getResponseCode();
                System.out.println("✅ Ping response code: " + responseCode);
            } catch (Exception e) {
                System.err.println("❌ Error pinging: " + e.getMessage());
            }
        }, 0, 5, TimeUnit.MINUTES);
}
}