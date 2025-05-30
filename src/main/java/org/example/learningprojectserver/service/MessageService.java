package org.example.learningprojectserver.service;

import lombok.RequiredArgsConstructor;
import org.example.learningprojectserver.notification.NotificationType;
import org.example.learningprojectserver.notification.dto.NotificationDTO;
import org.example.learningprojectserver.dto.MessageDTO;
import org.example.learningprojectserver.entities.MessageEntity;
import org.example.learningprojectserver.entities.UserEntity;
import org.example.learningprojectserver.mappers.MessageEntityToMessageDTOMapper;
import org.example.learningprojectserver.notification.dto.SystemMessageDTO;
import org.example.learningprojectserver.notification.publisher.NotificationEventPublisher;
import org.example.learningprojectserver.repository.MessageRepository;
import org.example.learningprojectserver.repository.UserRepository;
import org.example.learningprojectserver.response.BasicResponse;
import org.example.learningprojectserver.strategy.message.MessageRecipientStrategy;
import org.example.learningprojectserver.strategy.message.MessageRecipientStrategyFactory;
import org.example.learningprojectserver.utils.SmsSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.learningprojectserver.utils.SmsSender.sendSms;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final MessageRecipientStrategyFactory strategyFactory;
    private final MessageEntityToMessageDTOMapper messageEntityToMessageDTOMapper;
    private final NotificationEventPublisher notificationEventPublisher;
    private final CacheManager cacheManager;



    public BasicResponse getRecipientTypes(String userId) {
        UserEntity user = userRepository.findUserByUserId(userId);
        if (user == null) {
            return new BasicResponse(false, "משתמש לא נימצא");
        }

        List<String> recipientTypes;
        switch (user.getRole()) {
            case SYSTEM_ADMIN:
                recipientTypes = List.of("כל מנהלי בית ספר");
                break;
            case SCHOOLMANAGER:
//                recipientTypes = List.of("כיתה", "שכבה", "מורי-שכבה", "כל-המורים", "כל-התלמידים");
                recipientTypes = List.of("כל-המורים", "כל-התלמידים");

                break;
            case TEACHER:
                recipientTypes = List.of("כיתה","כל-התלמידים");
                break;
            case STUDENT:
                recipientTypes = List.of();
                break;
            default:
                recipientTypes = List.of();
                break;
        }

        BasicResponse basicResponse = new BasicResponse(true, null);
        basicResponse.setData(recipientTypes);
        return basicResponse;
    }

    public BasicResponse sendMessage(String senderId, String recipientType, String recipientValue, String title, String content) {
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
            return new BasicResponse(false, "אין נמענים לשליחת ההודעה");
        }

        List<String> recipientsPhoneNumber = recipients.stream()
                .map(UserEntity::getPhoneNumber)
                .toList();

        List<MessageEntity> messages = new ArrayList<>();

        MessageDTO messageDTO = new MessageDTO();
        messageDTO.setTitle(title);
        messageDTO.setContent(content);
        messageDTO.setSentAt(LocalDateTime.now());
        messageDTO.setSenderName(sender.getUsername());

        NotificationDTO<SystemMessageDTO> notificationDTO = new NotificationDTO<>(
                NotificationType.SYSTEM_MESSAGE,
                new SystemMessageDTO(messageDTO)
        );

        for (UserEntity receiver : recipients) {
            MessageEntity message = new MessageEntity();
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setTitle(title);
            message.setContent(content);
            message.setSentAt(LocalDateTime.now());

            messages.add(message);
        }

        List<String> recipientsIds = recipients.stream()
                .map(UserEntity::getUserId)
                .toList();

        notificationEventPublisher.publish(recipientsIds, notificationDTO);

        String smsMessage = "קיבלת הודעה חדשה במערכת. לצפייה היכנס: https://your-app.com/messages";
        sendSms(smsMessage, recipientsPhoneNumber);

        messageRepository.saveAll(messages);
        recipients.forEach(r ->
                cacheManager.getCache("receivedMessages").evict(r.getUserId())
        );

        return new BasicResponse(true, "ההודעה נשלחה בהצלחה ל־" + messages.size() + " נמענים");
    }


    @Cacheable(value = "receivedMessages", key = "#userId")
    public BasicResponse getAllRecivedMessages(String userId) {
        BasicResponse basicResponse = new BasicResponse();
        UserEntity user = userRepository.findUserByUserId(userId);
        if (user == null) {
            basicResponse.setSuccess(false);
            return basicResponse;
        }

        List<MessageEntity> messages = user.getReceivedMessages();
        List<MessageDTO> messageDTOS = messages.stream()
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


        List<MessageEntity> messages = user.getSentMessages();
        List<MessageDTO> messageDTOS = messages.stream()
                .map(messageEntityToMessageDTOMapper)
                .toList();
        basicResponse.setSuccess(true);
        basicResponse.setData(messageDTOS);
        return basicResponse;

    }

    }
