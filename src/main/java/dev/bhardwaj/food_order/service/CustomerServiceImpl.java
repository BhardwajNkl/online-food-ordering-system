package dev.bhardwaj.food_order.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.CustomerDetailsDto;
import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.NewCustomerDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;
import dev.bhardwaj.food_order.dto.converter.CustomerConverter;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository customerRepository;
	
	@Autowired
	private CustomerConverter customerConverter;
	
	
	public CustomerServiceImpl(CustomerRepository customerRepository) {
		this.customerRepository = customerRepository;
	}

	@Override
	public CustomerDto createCustomer(NewCustomerDto customerDto) {
		Customer customer = customerConverter.toEntity(customerDto);
		Customer createdCustomer = customerRepository.save(customer);
	
		return customerConverter.toDto(createdCustomer, CustomerDto.class);
	}

	@Override
	public CustomerDto updateCustomerDetails(UpdateCustomerDto customerDto) {
		
		Customer updatedCustomer = customerConverter.toEntity(customerDto);
		
		Customer savedUpdatedCustomer = customerRepository.save(updatedCustomer);
		
		return customerConverter.toDto(savedUpdatedCustomer, CustomerDto.class);
	}

	@Override
	public void deleteCustomer(long customerId) {
		customerRepository.deleteById(customerId);
	}

	@Override
	public CustomerDetailsDto getCustomerDetails(long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()->new RuntimeException("Customer doe not exist!"));
		
		return customerConverter.toDto(customer, CustomerDetailsDto.class);
		
	}

//	@Override
//	public Customer getCustomer(long customerId) {
//		return customerRepository.findById(customerId)
//				.orElseThrow(()->new RuntimeException("Customer doe not exist!"));
//	}
	
	@Override
	public void login() {
		
	}

}
