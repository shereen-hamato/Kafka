package com.ordering.order.service.model;

public record Order(String orderId, String status, double price) {
}