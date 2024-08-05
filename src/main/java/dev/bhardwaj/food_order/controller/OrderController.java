package dev.bhardwaj.food_order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.dto.BillDto;
import dev.bhardwaj.food_order.dto.NewOrderDto;
import dev.bhardwaj.food_order.dto.OrderDetailsDto;
import dev.bhardwaj.food_order.dto.OrderDto;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	
	private final OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping("/place-order")
	OrderDto placeOrder(@RequestBody NewOrderDto orderDto) {
		return orderService.placeOrder(orderDto);
	}
	
	@GetMapping("/get-order-details/{orderId}")
	OrderDetailsDto getOrderDetails(@PathVariable long orderId) {
		return orderService.getOrderDetails(orderId);
	}
	
	@GetMapping("/get-orders-for-customer/{customerId}")
	List<OrderDto> getOrdersForCustomer(@PathVariable long customerId){
		return orderService.getOrdersForCustomer(customerId);
	}
	
	@GetMapping("/get-orders-for-restaurant/{restaurantId}")
	List<OrderDto> getAllOrdersForRestaurant(@PathVariable int restaurantId){
		return orderService.getOrdersForRestaurant(restaurantId);
	}
	
	@GetMapping("/get-bill/{orderId}")
	BillDto getBill(@PathVariable long orderId) {
		return orderService.getBill(orderId);
	}
}
