package dev.bhardwaj.food_order.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.dto.CreateCustomerDto;
import dev.bhardwaj.food_order.dto.CreateOrderDto;
import dev.bhardwaj.food_order.dto.CreateRatingDto;
import dev.bhardwaj.food_order.dto.CreateReviewDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.entity.Review;
import dev.bhardwaj.food_order.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	private final CustomerService customerService;
	
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping("/create")
	Customer createCustomer(@RequestBody CreateCustomerDto customerDto) {
		return customerService.createCustomer(customerDto);
	}
	
	@PutMapping("/update")
	Customer updateCustomerDetails(@RequestBody UpdateCustomerDto customerDto) {
		return customerService.updateCustomerDetails(customerDto);
	}
	
	@DeleteMapping("/delete/{customerId}")
	void deleteCustomer(@PathVariable long customerId) {
		customerService.deleteCustomer(customerId);
	}
	
	@GetMapping("/get-customer-details/{customerId}")
	Customer getCustomerDetails(@PathVariable long customerId) {
		return customerService.getCustomerDetails(customerId);
	}
	
	void login() {
		
	}
	
	@PostMapping("/place-order")
	Order placeOrder(@RequestBody CreateOrderDto orderDto) {
		return customerService.placeOrder(orderDto);
	}
	
	@GetMapping("/get-orders-for-customer/{customerId}")
	List<Order> getOrdersForCustomer(@PathVariable long customerId){
		return customerService.getOrdersForCustomer(customerId);
	}
	
	@PostMapping("/add-rating")
	Rating addRating(@RequestBody CreateRatingDto ratingDto) {
		return customerService.addRating(ratingDto);
	}
	
	@PostMapping("/add-review")
	Review addReview(@RequestBody CreateReviewDto reviewDto) {
		return customerService.addReview(reviewDto);
	}
}
