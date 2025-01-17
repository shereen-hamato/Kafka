package com.ordering.order.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ordering.order.service.model.Order;
import com.ordering.order.service.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private  OrderService orderService;

    public ResponseEntity<Order> processOrder(@RequestBody Order order){
        // Process the order and send it to Kafka
        var processedOrder = orderService.processOrder(new Order("1","Created",order.price()));

        // Return the appropriate response based on whether processing succeeded
        return processedOrder
                .map(ResponseEntity::ok) // Return 200 OK with the processed order
                .orElseGet(() -> ResponseEntity.status(500).build()); // Return 500 Internal Server Error
    }

}
