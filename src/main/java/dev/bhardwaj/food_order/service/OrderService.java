package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.entity.Order;

public interface OrderService {
	void placeOrder(); // pass order dto
	List<Order> getOrdersForACusomer(long customerId);
	Order getOrderDetails(long orderId);
	void getBill(long orderId);
}
