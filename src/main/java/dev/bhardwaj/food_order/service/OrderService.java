package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.dto.BillDto;
import dev.bhardwaj.food_order.dto.NewOrderDto;
import dev.bhardwaj.food_order.dto.OrderDetailsDto;
import dev.bhardwaj.food_order.dto.OrderDto;

public interface OrderService {
	OrderDto placeOrder(NewOrderDto orderDto);
	OrderDetailsDto getOrderDetails(long orderId);
	BillDto getBill(long orderId);
	List<OrderDto> getOrdersForCustomer(long customerId);
	List<OrderDto> getOrdersForRestaurant(int restaurantId);
	void updateOrderDeliveryStatus(long orderId, String newStatus);
}
