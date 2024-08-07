package dev.bhardwaj.food_order.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
	ResponseEntity<CustomerDto> createCustomer(@Valid @RequestBody NewCustomerDto customerDto) {
		CustomerDto result = customerService.createCustomer(customerDto);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
	}
	
	@PutMapping("/update")
	ResponseEntity<CustomerDto> updateCustomerDetails(@Valid @RequestBody UpdateCustomerDto customerDto, @AuthenticationPrincipal SecurityUser securityUser) {
		if(securityUser.getUser().getCustomer().getId()==customerDto.getId()) {
			CustomerDto result = customerService.updateCustomerDetails(customerDto);
	        return new ResponseEntity<>(result, HttpStatus.OK);
			
		} else {
			throw new NotAllowedException("You can only update your own details");
		}
	}
	
	@DeleteMapping("/delete/{customerId}")
	ResponseEntity<?> deleteCustomer(@PathVariable long customerId, @AuthenticationPrincipal SecurityUser securityUser) {
		if(securityUser.getUser().getCustomer().getId()==customerId) {
			customerService.deleteCustomer(customerId);
	        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} else {
			throw new NotAllowedException("You can only delete your own account");
		}
	}
	
	@GetMapping("/get-customer-details/{customerId}")
	ResponseEntity<CustomerDetailsDto> getCustomerDetails(@PathVariable long customerId,
			@AuthenticationPrincipal SecurityUser securityUser ) {
		if(securityUser.getUser().getCustomer().getId()==customerId) {
			CustomerDetailsDto result = customerService.getCustomerDetails(customerId);
	        return new ResponseEntity<>(result,HttpStatus.OK);
		} else {
			throw new NotAllowedException("You can only get your own customer details");
		}
	}
	
	@PostMapping("/login")
	ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto) {
		String token = customerService.login(loginDto);
        return new ResponseEntity<String>(token, HttpStatus.OK);
	}
}
