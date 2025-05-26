package org.example.learningprojectserver.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.learningprojectserver.dto.UpcomingEventDto;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HolidayUtils {


    public static List<UpcomingEventDto> fetchHebrewHolidayEvents() {
        String url = "https://www.hebcal.com/hebcal?cfg=json&maj=on&start=2025-05-26&end=2025-08-26&geo=il&lg=he";
        RestTemplate restTemplate = new RestTemplate();
        List<UpcomingEventDto> events = new ArrayList<>();

        try {
            String json = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(json);
            JsonNode items = root.get("items");

            if (items != null && items.isArray()) {
                for (JsonNode item : items) {

                    String title = item.get("title").asText();
                    String date = item.get("date").asText();
                    String category = item.get("category").asText();
                    String subcat  = item.path("subcat").asText("");

                    if("holiday".equals(category)&&"fast".equals(subcat)
                            || "holiday".equals(category)&&"major".equals(subcat)
                            || "holiday".equals(category)&&"minor".equals(subcat)){
                        int days = daysUntilHebcal(date);

                        if (days >= 0) {
                            events.add(new UpcomingEventDto(title, days));
                        }
                    }

                }
            }

        } catch (Exception e) {

        }

        return events;
    }
    public static int daysUntilHebcal(String dateStr) {
        LocalDate eventDate = LocalDate.parse(dateStr);
        LocalDate today = LocalDate.now();
        return (int) ChronoUnit.DAYS.between(today, eventDate);
    }



    public static int daysUntil(String startTime) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        LocalDateTime examDateTime = LocalDateTime.parse(startTime, fmt);

        ZoneId ilZone = ZoneId.of("Asia/Jerusalem");

        ZonedDateTime examZoned = examDateTime.atZone(ilZone);

        LocalDate today = LocalDate.now(ilZone);

        LocalDate examDate = examZoned.toLocalDate();

        return (int) ChronoUnit.DAYS.between(today, examDate);
    }
}
