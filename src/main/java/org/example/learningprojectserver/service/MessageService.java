package org.example.learningprojectserver.service;

import org.example.learningprojectserver.dto.MessageDTO;
import org.example.learningprojectserver.entities.MessageEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.mappers.MessageEntityToMessageDTOMapper;
import org.example.learningprojectserver.repository.MessageRepository;
import org.example.learningprojectserver.repository.UserRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.strategy.message.MessageRecipientStrategy;
import org.example.learningprojectserver.strategy.message.MessageRecipientStrategyFactory;
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
    private final MessageEntityToMessageDTOMapper messageEntityToMessageDTOMapper;


    @Autowired
    public MessageService(UserRepository userRepository, MessageRepository messageRepository, MessageRecipientStrategyFactory strategyFactory, NotificationService notificationService, MessageEntityToMessageDTOMapper messageEntityToMessageDTOMapper) {
        this.userRepository = userRepository;
        this.messageRepository = messageRepository;
        this.strategyFactory = strategyFactory;
        this.notificationService = notificationService;
        this.messageEntityToMessageDTOMapper = messageEntityToMessageDTOMapper;
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

            MessageDTO messageDTO = new MessageDTO();
            messageDTO.setTitle(title);
            messageDTO.setContent(content);
            messageDTO.setSentAt(LocalDateTime.now());
            messageDTO.setSenderName(sender.getUsername());

            notificationService.sendNotification(receiver.getUserId(),messageDTO);

        }
        String smsMessage = "קיבלת הודעה חדשה במערכת. לצפייה היכנס: https://your-app.com/messages";

        sendSms(smsMessage,recipientsPhoneNumber);
        userRepository.saveAll(recipients);

        return new BasicResponse(true, "ההודעה נשלחה בהצלחה ל־" + messages.size() + " נמענים");
    }

    public BasicResponse getAllRecivedMessages(String userId) {
        BasicResponse basicResponse = new BasicResponse();
        UserEntity user = userRepository.findUserByUserId(userId);
        if (user == null) {
            basicResponse.setSuccess(false);
            return basicResponse;
        }

        List<MessageEntity> messages = user.getReceivedMessages();
        List<MessageDTO> messageDTOS= messages.stream()
                .map(messageEntityToMessageDTOMapper)
                .toList();
        basicResponse.setSuccess(true);
        basicResponse.setData(messageDTOS);
        return basicResponse;

    }
    //Todo לשים בקונטרולר
    public BasicResponse getAllSentMessages(String userId) {
        BasicResponse basicResponse = new BasicResponse();
        UserEntity user = userRepository.findUserByUserId(userId);
        if (user == null) {
            basicResponse.setSuccess(false);
            return basicResponse;
        }

        //TODO TO to test git

        List<MessageEntity> messages = user.getSentMessages();
        List<MessageDTO> messageDTOS= messages.stream()
                .map(messageEntityToMessageDTOMapper)
                .toList();
        basicResponse.setSuccess(true);
        basicResponse.setData(messageDTOS);
        return basicResponse;

    }

    }
