package dev.bhardwaj.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
import dev.bhardwaj.food_order.dto.LoginDto;
import dev.bhardwaj.food_order.dto.NewCustomerDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;
import dev.bhardwaj.food_order.exception.NotAllowedException;
import dev.bhardwaj.food_order.security.SecurityUser;
import dev.bhardwaj.food_order.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;

@RestController
@RequestMapping("/api/customer")
@Validated
public class CustomerController {
	
	private final CustomerService customerService;
	
	@Autowired
	public CustomerController(CustomerService customerService) {
		this.customerService = customerService;
	}
	
	@PostMapping("/create")
	CustomerDto createCustomer(@Valid @RequestBody NewCustomerDto customerDto) {
		return customerService.createCustomer(customerDto);
	}
	
	@PutMapping("/update")
	CustomerDto updateCustomerDetails(@Valid @RequestBody UpdateCustomerDto customerDto, @AuthenticationPrincipal SecurityUser securityUser) {
		if(securityUser.getUser().getCustomer().getId()==customerDto.getId()) {
			return customerService.updateCustomerDetails(customerDto);
		} else {
			throw new NotAllowedException("You can only update your own details");
		}
	}
	
	@DeleteMapping("/delete/{customerId}")
	void deleteCustomer(@PathVariable long customerId, @AuthenticationPrincipal SecurityUser securityUser) {
		if(securityUser.getUser().getCustomer().getId()==customerId) {
			customerService.deleteCustomer(customerId);
		} else {
			throw new NotAllowedException("You can only delete your own account");
		}
	}
	
	@GetMapping("/get-customer-details/{customerId}")
	CustomerDetailsDto getCustomerDetails(@PathVariable long customerId,
			@AuthenticationPrincipal SecurityUser securityUser ) {
		if(securityUser.getUser().getCustomer().getId()==customerId) {
			return customerService.getCustomerDetails(customerId);
		} else {
			throw new NotAllowedException("You can only get your own customer details");
		}
	}
	
	@PostMapping("/login")
	String login(@Valid @RequestBody LoginDto loginDto) {
		return customerService.login(loginDto);
	}
}
