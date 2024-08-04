package dev.bhardwaj.food_order.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@GetMapping("/get-order-details/{orderId}")
	Order getOrderDetails(@PathVariable long orderId) {
		return orderService.getOrderDetails(orderId);
	}
	
	@GetMapping("/get-bill/{orderId}")
	void getBill(@PathVariable long orderId) {
		// not implemented the business logic
	}
}
