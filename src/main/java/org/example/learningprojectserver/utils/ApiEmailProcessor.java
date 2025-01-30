package org.example.learningprojectserver.utils;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.mail.*;
import javax.mail.internet.*;

public class ApiEmailProcessor {

    private static final String SENDER_EMAIL = "shai27133@gmail.com"; // האימייל השולח
    private static final String SENDER_PASSWORD = "jkey sefo oeus jgnp"; // הסיסמה/סיסמת האפליקציה
    private static final String PERSONAL = "learning app"; // שם אישי שיוצג בשורת השולח
    private static final ExecutorService emailExecutor = Executors.newCachedThreadPool();
    public static void main(String[] args) {
        sendEmail("shonberko@gmail.com","reez","שון יאלוף");
    }

    public static void sendEmail(String recipient, String subject, String content) {
        emailExecutor.execute(() -> sendEmail1(recipient, subject, content));
    }
    public static boolean sendEmail1(String recipient, String subject, String content) {
        final String host = "smtp.gmail.com";
        final int port = 465;

        // הגדרת מאפייני ה-SMTP
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", String.valueOf(port));
        properties.put("mail.smtp.connectiontimeout", "10000");
        properties.put("mail.smtp.timeout", "10000");
        properties.put("mail.smtp.writetimeout", "10000");

        // יצירת סשן עם האותנטיקציה
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(SENDER_EMAIL, SENDER_PASSWORD);
            }
        });

        try {
            // יצירת הודעת אימייל
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(SENDER_EMAIL, PERSONAL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
            message.setSubject(subject);

            // טקסט ההודעה בפורמט HTML
            MimeBodyPart textPart = new MimeBodyPart();
            String htmlContent = "<html><body>"
                    + "<h3>" + subject + "</h3>"
                    + "<p>" + content.replace("\n", "<br>") + "</p>"
                    + "</body></html>";
            textPart.setContent(htmlContent, "text/html; charset=UTF-8");

            // תמונה מצורפת להודעה
            MimeBodyPart imagePart = new MimeBodyPart();
//            File imageFile = new ClassPathResource("SocialNetwork.png").getFile();
//            imagePart.attachFile(imageFile);
//            imagePart.setContentID("<profileImage>");
//            imagePart.setDisposition(MimeBodyPart.INLINE);

            // שילוב חלקי האימייל ל-Multipart
            MimeMultipart multipart = new MimeMultipart();
            multipart.addBodyPart(textPart);
//            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            // שליחת האימייל
            Transport.send(message);
            System.out.println("Email sent successfully to " + recipient);
            return true;
        } catch (MessagingException e) {
            System.out.println("Error sending email: " + e.getMessage());
            return false;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage(), e);
        }
    }
}
