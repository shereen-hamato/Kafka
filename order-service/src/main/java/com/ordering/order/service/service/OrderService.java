package com.ordering.order.service.service;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ordering.order.service.model.Order;

import java.util.Optional;

@Service
public class OrderService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${kafka.topic.order-events}")
    private String topic;

    public OrderService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public Optional<Order> processOrder(Order order) {
        try {
            // Send order event to Kafka
            kafkaTemplate.send(topic, order.orderId(), order.toString());
            System.out.println("Order event sent: " + order.toString());
            return Optional.of(order);
        } catch (Exception e) {
            // Log the error for debugging
            System.err.println("Failed to send order event: " + e.getMessage());
            e.printStackTrace();
        }

        return Optional.empty();
    }
}
