package org.example.learningprojectserver.service.MathQuestion;

import org.example.learningprojectserver.entities.QuestionEntity;

import java.util.Random;

public class DiscountQuestion implements MathQuestion {

    private final Random random = new Random();

    @Override
    public QuestionEntity generateQuestion(int difficulty) {
        // בוחרים שאלה אקראית – חישוב הנחה או מחיר אחרי הנחה
        int problemType = random.nextInt(2);

        // שאלה 1: חישוב מחיר אחרי הנחה
        if (problemType == 0) {
            return generatePriceAfterDiscountQuestion(difficulty);
        }
        // שאלה 2: חישוב המחיר המקורי על פי המחיר אחרי הנחה
        else {
            return generateOriginalPriceQuestion(difficulty);
        }
    }

    // חישוב מחיר אחרי הנחה
    private QuestionEntity generatePriceAfterDiscountQuestion(int difficulty) {
        // הגדרת המחיר המקורי ומידת ההנחה
        int originalPrice = getOriginalPrice(difficulty);
        int discountPercentage = getDiscountPercentage(difficulty);

        // חישוב המחיר אחרי הנחה כך שהתוצאה תמיד תהיה שלם (מספר שלם)
        int discountAmount = originalPrice * discountPercentage / 100;
        int priceAfterDiscount = originalPrice - discountAmount;

        // בניית השאלה
        String questionText = String.format("מוצר עולה %d ש\"ח ויש עליו הנחה של %d%%. כמה יש לשלם?",
                originalPrice, discountPercentage);
        String answer = String.format("%d", priceAfterDiscount);

        return new QuestionEntity("מתמטיקה", "חישוב הנחות", questionText, answer);
    }

    private QuestionEntity generateOriginalPriceQuestion(int difficulty) {
        // הגדרת המחיר אחרי הנחה ומידת ההנחה
        int priceAfterDiscount = getPriceAfterDiscount(difficulty);
        int discountPercentage = getDiscountPercentage(difficulty);

        // חישוב המחיר המקורי כך שהתוצאה תהיה תמיד שלם (מספר שלם)
        int originalPrice = priceAfterDiscount * 100 / (100 - discountPercentage);

        // תיקון: כדי שהמחיר יהיה תמיד שלם, נוודא שהחישוב יוציא תוצאה של שלם
        // לדוגמה, אם המחיר אחרי הנחה הוא 120 וההנחה היא 12%, המחיר המקורי יהיה 136 ולא 136.36

        // בניית השאלה
        String questionText = String.format("אם המחיר אחרי הנחה של %d%% הוא %d ש\"ח, מה היה המחיר המקורי?",
                discountPercentage, priceAfterDiscount);
        String answer = String.format("%d", originalPrice);

        return new QuestionEntity("מתמטיקה", "חישוב הנחות", questionText, answer);
    }

    // פונקציות עזר להפקת מחיר או אחוז הנחה בצורה משתנה לפי רמת הקושי
    private int getOriginalPrice(int difficulty) {
        switch (difficulty) {
            case 1: return (random.nextInt(10) + 1) * 10; // בין 10 ל-100 ש"ח
            case 2: return (random.nextInt(10) + 5) * 20; // בין 100 ל-200 ש"ח
            case 3: return (random.nextInt(5) + 10) * 50; // בין 500 ל-1000 ש"ח
            case 4: return (random.nextInt(3) + 5) * 100; // בין 1000 ל-1500 ש"ח
            case 5: return (random.nextInt(2) + 15) * 100; // בין 1500 ל-3000 ש"ח
            default: return 100; // ברירת מחדל
        }
    }

    private int getPriceAfterDiscount(int difficulty) {
        switch (difficulty) {
            case 1: return (random.nextInt(10) + 1) * 10; // בין 10 ל-100 ש"ח
            case 2: return (random.nextInt(5) + 5) * 20; // בין 50 ל-150 ש"ח
            case 3: return (random.nextInt(5) + 2) * 50; // בין 100 ל-300 ש"ח
            case 4: return (random.nextInt(2) + 4) * 100; // בין 200 ל-500 ש"ח
            case 5: return (random.nextInt(2) + 7) * 100; // בין 700 ל-1400 ש"ח
            default: return 100; // ברירת מחדל
        }
    }

    private int getDiscountPercentage(int difficulty) {
        switch (difficulty) {
            case 1: return random.nextInt(10) + 5; // בין 5 ל-15%
            case 2: return random.nextInt(10) + 10; // בין 10 ל-20%
            case 3: return random.nextInt(10) + 20; // בין 20 ל-30%
            case 4: return random.nextInt(10) + 25; // בין 25 ל-35%
            case 5: return random.nextInt(10) + 30; // בין 30 ל-40%
            default: return 10; // ברירת מחדל
        }
    }
}
