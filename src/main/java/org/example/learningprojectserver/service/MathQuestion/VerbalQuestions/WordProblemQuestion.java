//package org.example.learningprojectserver.service.question;
//
//import com.github.javafaker.Faker;
//import org.example.learningprojectserver.entities.QuestionEntity;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;
//
//public class WordProblemQuestion implements MathQuestion {
//
//    private final Random random = new Random();
//    private final Faker faker = new Faker(new java.util.Locale("he"));
//
//    @Override
//    public QuestionEntity generateQuestion(int difficulty) {
//        List<String> products = new ArrayList<String>();
//        String newProduct = "";
//        for (int i = 0; i < 10; i++) {
//            newProduct= i%2==0 ? faker.food().fruit():faker.food().vegetable();
//            products.add(newProduct);
//        }
//
//        String name1 = faker.name().firstName();
//        String name2 = faker.name().firstName();
//
//        String product = products.get(random.nextInt(products.size()));
//
//        int totalItems = getTotalItemsBasedOnDifficulty(difficulty);
//        int itemsTransacted = random.nextInt(totalItems / 2) + 1;
//
//        boolean isGiven = random.nextBoolean();
//
//        String questionText;
//        int itemsLeft;
//
//        if (isGiven) {
//            itemsLeft = totalItems - itemsTransacted;
//            questionText = String.format(
//                    " %s היו לו %d %s. הוא נתן %d %s ל%s. כמה %s נשארו לו?"//TODO לסדר את הפורמט
//                    ,
//                    name1, totalItems, product, itemsTransacted, product, name2, product
//            );
//        } else {
//
//            itemsLeft = totalItems + itemsTransacted;
//            questionText = String.format(
//                    "%s היו לו %d %s. הוא קיבל %d %s מ%s. כמה %s יש לו עכשיו ?",//TODO לסדר את הפורמט
//                    name1, totalItems, product, itemsTransacted, product, name2, product
//            );
//        }
//
//        String answer = String.valueOf(itemsLeft);
//
//        return new QuestionEntity("מתמטיקה", "שאלה מילולית", questionText, answer);
//    }
//
//    private int getTotalItemsBasedOnDifficulty(int difficulty) {
//        return switch (difficulty) {
//            case 1 -> random.nextInt(5) + 5;
//            case 2 -> random.nextInt(10) + 5;
//            case 3 -> random.nextInt(15) + 10;
//            case 4 -> random.nextInt(20) + 20;
//            case 5 -> random.nextInt(30) + 30;
//            default -> throw new IllegalArgumentException("דרגת קושי לא תקינה");
//        };
//    }
//}
package org.example.learningprojectserver.service.MathQuestion.VerbalQuestions;

import com.github.javafaker.Faker;
import org.example.learningprojectserver.entities.QuestionEntity;
import org.example.learningprojectserver.service.QuestionGenerator;

import java.util.List;
import java.util.Random;

public class WordProblemQuestion implements QuestionGenerator {

    private final Random random = new Random();
    private final Faker faker = new Faker(new java.util.Locale("he"));

    @Override
    public QuestionEntity generateQuestion(int difficulty) {

        List<String> products = List.of("בננות", "תפוחים", "עגבניות", "קולפנים", "תפוזים");
        String name1 = faker.name().firstName();
        String name2 = faker.name().firstName();

        String product = products.get(random.nextInt(products.size()));

        int totalItems = getTotalItemsBasedOnDifficulty(difficulty);
        int itemsTransacted = random.nextInt(totalItems / 2) + 1;

        boolean isGiven = random.nextBoolean();

        String questionText;
        int itemsLeft;

        if (isGiven) {
            itemsLeft = totalItems - itemsTransacted;
            questionText = String.format(
                    "%s היו לו %d %s. הוא נתן %d %s ל%s. כמה %s נשארו לו?",
                    name1, totalItems, product, itemsTransacted, product, name2, product
            );
        } else {
            itemsLeft = totalItems + itemsTransacted;
            questionText = String.format(
                    "%s היו לו %d %s. הוא קיבל %d %s מ%s. כמה %s יש לו עכשיו?",
                    name1, totalItems, product, itemsTransacted, product, name2, product
            );
        }

        String answer = String.valueOf(itemsLeft);
        System.out.println(questionText);
        System.out.println(answer);
        return new QuestionEntity("מתמטיקה","שאלות מילוליות", "שאלה מילולית", questionText, answer);
    }

    private int getTotalItemsBasedOnDifficulty(int difficulty) {
        return switch (difficulty) {
            case 1 -> random.nextInt(5) + 5;
            case 2 -> random.nextInt(10) + 5;
            case 3 -> random.nextInt(15) + 10;
            case 4 -> random.nextInt(20) + 20;
            case 5 -> random.nextInt(30) + 30;
            default ->random.nextInt(30) + 30;
        };
    }
}
