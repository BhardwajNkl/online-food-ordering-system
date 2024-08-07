package dev.bhardwaj.food_order.service;

import dev.bhardwaj.food_order.dto.CustomerDetailsDto;
import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.LoginDto;
import dev.bhardwaj.food_order.dto.NewCustomerDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;

public interface CustomerService {
	CustomerDto createCustomer(NewCustomerDto customerDto);
	CustomerDto updateCustomerDetails(UpdateCustomerDto customerDto);	
	void deleteCustomer(long id);
	CustomerDetailsDto getCustomerDetails(long id);
	String login(LoginDto loginDto);
}
