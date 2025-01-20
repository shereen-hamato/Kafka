package com.notification.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @KafkaListener(topics = {"order-events","payment-events"}, groupId = "notification-service")
    public void handleNotifications(String message){
        System.out.println("Notification sent for event "+message);

    }
}
