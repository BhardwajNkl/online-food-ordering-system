package dev.bhardwaj.food_order.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.dto.CustomerDetailsDto;
import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.NewCustomerDto;
import dev.bhardwaj.food_order.dto.OrderDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.exception.DoesNotExistException;
import dev.bhardwaj.food_order.exception.DtoEntityConversionException;
import dev.bhardwaj.food_order.repository.CustomerRepository;

@Component
public class CustomerConverter {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	@Lazy
	private OrderConverter orderConverter;
	
	public Customer toEntity(Object dto) {
		if (dto == null) {
			return null;
		}

		switch (dto.getClass().getSimpleName()) {
		case "NewCustomerDto":
			Customer customer = new Customer();
			NewCustomerDto newDto = (NewCustomerDto) dto;
			customer.setName(newDto.getName());
			customer.setEmail(newDto.getEmail());
			Customer.Address address = new Customer.Address(newDto.getLocality(),
					newDto.getCity(),newDto.getState(),newDto.getPinCode());
			customer.setAddress(address);

			return customer;
			
		case "UpdateCustomerDto":
			UpdateCustomerDto updateDto = (UpdateCustomerDto) dto;
			Customer customerToUpdate = customerRepository
					.findById(updateDto.getId())
					.orElseThrow(()->new DoesNotExistException("Customer with given id does not exist!"));
			customerToUpdate.setName(updateDto.getName());
			customerToUpdate.setEmail(updateDto.getEmail());
			Customer.Address addressToUpdate = new Customer.Address(updateDto.getLocality(),
					updateDto.getCity(),updateDto.getState(),updateDto.getPinCode());
			customerToUpdate.setAddress(addressToUpdate);
			return customerToUpdate;
			
		default:
			throw new DtoEntityConversionException("DTO to Customer Entity conversion failed");
		}
	}

	public <T> T toDto(Customer customer, Class<T> dtoClass) {
		if (customer == null) {
			return null;
		}

		switch (dtoClass.getSimpleName()) {
		
		case "CustomerDto":
			CustomerDto customerDto = new CustomerDto();
			customerDto.setId(customer.getId());
			customerDto.setName(customer.getName());
			customerDto.setEmail(customer.getEmail());
			customerDto.setAddress(customer.getAddress());
			return dtoClass.cast(customerDto);
			
		case "CustomerDetailsDto":
			CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
			customerDetailsDto.setId(customer.getId());
			customerDetailsDto.setName(customer.getName());
			customerDetailsDto.setEmail(customer.getEmail());
			customerDetailsDto.setAddress(customer.getAddress());
			
			
			List<OrderDto> orders = customer.getOrders()
					.stream()
					.map(
						order->{
							return orderConverter.toDto(order, OrderDto.class);
						}
						)
					.collect(Collectors.toList());
			
			
			customerDetailsDto.setOrders(orders);
			
			
			return dtoClass.cast(customerDetailsDto);
			
		default:
			throw new DtoEntityConversionException("Customer entity to DTO conversion failed");
		}

	}
}
