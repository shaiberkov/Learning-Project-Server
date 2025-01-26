package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Random;

public class TimeProblemQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        int problemType = random.nextInt(3);

        if (problemType == 0) {
            return generateAdditionQuestion(difficulty);
        }
        else if (problemType == 1) {
            return generateSubtractionQuestion(difficulty);
        }
        else {
            return generateTravelTimeQuestion(difficulty);
        }
    }

    private QuestionEntity generateAdditionQuestion(int difficulty) {
        LocalTime startTime = LocalTime.of(random.nextInt(24), random.nextInt(60));
        int hoursToAdd = random.nextInt(5 * difficulty) + 1;
        int minutesToAdd = random.nextInt(60 * difficulty);

        LocalTime resultTime = startTime.plusHours(hoursToAdd).plusMinutes(minutesToAdd);

        String questionText = String.format("אם השעה עכשיו היא %s, והוספנו %d שעות ו-%d דקות, מתי תהיה השעה?",
                startTime, hoursToAdd, minutesToAdd);
        String answer = resultTime.toString();

        return new QuestionEntity("מתמטיקה", "חישוב זמן", questionText, answer);
    }

    private QuestionEntity generateSubtractionQuestion(int difficulty) {
        LocalTime startTime = LocalTime.of(random.nextInt(24), random.nextInt(60));
        int hoursToSubtract = random.nextInt(5 * difficulty) + 1;
        int minutesToSubtract = random.nextInt(60 * difficulty);

        LocalTime resultTime = startTime.minusHours(hoursToSubtract).minusMinutes(minutesToSubtract);

        String questionText = String.format("אם השעה עכשיו היא %s, והורדנו %d שעות ו-%d דקות, מתי תהיה השעה?",
                startTime, hoursToSubtract, minutesToSubtract);
        String answer = resultTime.toString();

        return new QuestionEntity("מתמטיקה", "חישוב זמן", questionText, answer);
    }

    private QuestionEntity generateTravelTimeQuestion(int difficulty) {
        LocalTime startTime = LocalTime.of(random.nextInt(24), random.nextInt(60));
        LocalTime endTime = startTime.plusHours(random.nextInt(5 * difficulty) + 1).plusMinutes(random.nextInt(60 * difficulty)); // הוספת זמן אקראי בין 1 ל-5 שעות

        if (endTime.isBefore(startTime)) {
            endTime = endTime.plusHours(24);
        }


        Duration duration = Duration.between(startTime, endTime);

        String questionText = String.format("אם רכבת יצאה בשעה %s והגיעה בשעה %s, כמה זמן לקח לה להגיע?",
                startTime, endTime);
        String answer = formatDuration(duration);

        return new QuestionEntity("מתמטיקה", "חישוב זמן", questionText, answer);
    }


    private String formatDuration(Duration duration) {
        long hours = duration.toHours();
        long minutes = duration.toMinutesPart();

        String hoursText = (hours == 1) ? "שעה" : "שעות";
        String minutesText = (minutes == 1) ? "דקה" : "דקות";

        if (hours > 0) {
            return String.format("%d %s ו-%d %s", hours, hoursText, minutes, minutesText);
        } else {
            return String.format("%d %s", minutes, minutesText);
        }
    }
}
