package dev.bhardwaj.food_order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
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
import dev.bhardwaj.food_order.exception.NotAllowedException;
import dev.bhardwaj.food_order.security.SecurityUser;
import dev.bhardwaj.food_order.service.OrderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order")
@Validated
public class OrderController {
	
	private final OrderService orderService;
	
	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@PostMapping("/place-order")
	ResponseEntity<OrderDto> placeOrder(@Valid @RequestBody NewOrderDto orderDto) {
		OrderDto result = orderService.placeOrder(orderDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@GetMapping("/get-order-details/{orderId}")
	ResponseEntity<OrderDetailsDto> getOrderDetails(@PathVariable long orderId) {
		OrderDetailsDto result = orderService.getOrderDetails(orderId);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/get-orders-for-customer/{customerId}")
	ResponseEntity<List<OrderDto>> getOrdersForCustomer(@PathVariable long customerId, @AuthenticationPrincipal SecurityUser securityUser){
		if(securityUser.getUser().getCustomer().getId()==customerId) {
			List<OrderDto> result = orderService.getOrdersForCustomer(customerId);
	        return new ResponseEntity<>(result, HttpStatus.OK);

		} else {
			throw new NotAllowedException("You can get only your own list of orders");
		}
	}
	
	@GetMapping("/get-orders-for-restaurant/{restaurantId}")
	ResponseEntity<List<OrderDto>> getAllOrdersForRestaurant(@PathVariable int restaurantId){
		List<OrderDto> result = orderService.getOrdersForRestaurant(restaurantId);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/get-bill/{orderId}")
	ResponseEntity<BillDto> getBill(@PathVariable long orderId) {
		BillDto result = orderService.getBill(orderId);
        return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/update-order-delivery-status/{orderId}/{newStatus}")
	ResponseEntity<Boolean> updateOrderDeliveryStatus(@PathVariable long orderId ,@PathVariable String newStatus) {
		orderService.updateOrderDeliveryStatus(orderId, newStatus);
        return new ResponseEntity<>(true, HttpStatus.OK);

	}
}
