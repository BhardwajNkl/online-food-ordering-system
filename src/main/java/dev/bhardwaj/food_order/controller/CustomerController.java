package dev.bhardwaj.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.bhardwaj.food_order.dto.CustomerDetailsDto;
import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.NewCustomerDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;
import dev.bhardwaj.food_order.service.CustomerService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
	
	private final CustomerService customerService;
	
	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping("/create")
	CustomerDto createCustomer(@RequestBody NewCustomerDto customerDto) {
		return customerService.createCustomer(customerDto);
	}
	
	@PutMapping("/update")
	CustomerDto updateCustomerDetails(@RequestBody UpdateCustomerDto customerDto) {
		return customerService.updateCustomerDetails(customerDto);
	}
	
	@DeleteMapping("/delete/{customerId}")
	void deleteCustomer(@PathVariable long customerId) {
		customerService.deleteCustomer(customerId);
	}
	
	@GetMapping("/get-customer-details/{customerId}")
	CustomerDetailsDto getCustomerDetails(@PathVariable long customerId) {
		return customerService.getCustomerDetails(customerId);
	}
	
	void login() {
		
	}
}
