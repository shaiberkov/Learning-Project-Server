package org.example.learningprojectserver.service;

import jakarta.annotation.PostConstruct;
import org.example.learningprojectserver.entities.MessageEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.repository.MessageRepository;
import org.example.learningprojectserver.repository.UserRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.strategy.message.MessageRecipientStrategy;
import org.example.learningprojectserver.strategy.message.MessageRecipientStrategyFactory;
import org.example.learningprojectserver.utils.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.learningprojectserver.utils.SmsSender.sendSms;

@Service
public class MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageRecipientStrategyFactory strategyFactory;
    private final NotificationService notificationService;

    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository, MessageRecipientStrategyFactory strategyFactory, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.strategyFactory = strategyFactory;
        this.notificationService = notificationService;
    }


    public BasicResponse sendMessage(String senderId,String recipientType,String recipientValue, String title, String content) {
        UserEntity sender = userRepository.findUserByUserId(senderId);

        if (sender == null) {
            return new BasicResponse(false, "השולח לא קיים");
        }
        if (title == null || title.trim().isEmpty()) {
            return new BasicResponse(false, "הכותרת לא יכולה להיות ריקה");
        }

        if (content == null || content.trim().isEmpty()) {
            return new BasicResponse(false, "התוכן לא יכול להיות ריק");
        }


        MessageRecipientStrategy strategy = strategyFactory.getStrategy(recipientType, recipientValue);
        List<UserEntity> recipients = strategy.getRecipients(senderId);
        if (recipients.isEmpty()) {
            return new BasicResponse(false,"אין נמענים לשליחת ההודעה");
        }
        List<String> recipientsPhoneNumber = recipients.stream().map(UserEntity::getPhoneNumber).toList();
        List<MessageEntity> messages = new ArrayList<>();

        for (UserEntity receiver : recipients) {
            MessageEntity message = new MessageEntity();
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setTitle(title);
            message.setContent(content);
            message.setSentAt(LocalDateTime.now());

            sender.getSentMessages().add(message);
            receiver.getReceivedMessages().add(message);

            messages.add(message);

            String[] messageData = new String[] {title, content};
            notificationService.sendNotification(receiver.getUserId(),content);


        }
        String smsMessage = "קיבלת הודעה חדשה במערכת. לצפייה היכנס: https://your-app.com/messages";

        sendSms(smsMessage,recipientsPhoneNumber);
        messageRepository.saveAll(messages);

        return new BasicResponse(true, "ההודעה נשלחה בהצלחה ל־" + messages.size() + " נמענים");
    }


}
