package com.payment_service.payment_service.service;

import org.springframework.stereotype.Service;

import com.payment_service.payment_service.model.Order;
import com.payment_service.payment_service.model.Payment;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class PaymentService {

    @Autowired
    private KafkaTemplate<String, Payment> kafkaTemplate;

    private final String outputTopic = "payment-events";

    @KafkaListener(topics = "order-events", groupId = "payment-service")
    public void processOrder(ConsumerRecord<String, Order> record) {
        try {
            System.out.printf("Processing order: %s%n", record.value());

            // Produce payment success event
            Payment paymentEvent = new Payment(record.value().orderId(),"Sucess");
            kafkaTemplate.send(outputTopic, record.key(), paymentEvent);

            System.out.printf("Payment event sent for order ID: %s%n", record.key());
        } catch (Exception e) {
            System.err.printf("Failed to process order ID: %s, Error: %s%n", record.key(), e.getMessage());
        }
    }
}
