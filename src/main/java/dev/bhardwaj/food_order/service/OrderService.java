package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.dto.CreateOrderDto;
import dev.bhardwaj.food_order.entity.Order;

public interface OrderService {
	Order placeOrder(CreateOrderDto orderDto);
	Order getOrderDetails(long orderId);
	void getBill(long orderId);

//	List<Order> getOrdersForCustomer(long customerId); // customer has reference to orders list so no need here
	List<Order> getOrdersForRestaurant(int restaurantId);
}
