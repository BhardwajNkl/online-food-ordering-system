package dev.bhardwaj.food_order.service;

import dev.bhardwaj.food_order.dto.CustomerDetailsDto;
import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.NewCustomerDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;
import dev.bhardwaj.food_order.entity.Customer;

public interface CustomerService {
	CustomerDto createCustomer(NewCustomerDto customerDto);
	CustomerDto updateCustomerDetails(UpdateCustomerDto customerDto);
//	Customer getCustomer(long customerId);
	
	void deleteCustomer(long id);
	CustomerDetailsDto getCustomerDetails(long id);
	
	void login();
}
