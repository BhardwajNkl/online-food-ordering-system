package dev.bhardwaj.food_order.dto.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.DishDto;
import dev.bhardwaj.food_order.dto.NewOrderDto;
import dev.bhardwaj.food_order.dto.OrderDetailsDto;
import dev.bhardwaj.food_order.dto.OrderDto;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.entity.Order.DeliveryStatus;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.DishRepository;

@Component
public class OrderConverter {
	
	@Autowired
	private DishRepository dishRepository;
	@Autowired
	private CustomerRepository customerRepository;
	@Autowired
	private DishConverter dishConverter;
	@Autowired
	private CustomerConverter customerConverter;
	
	public Order toEntity(Object dto) {
		if (dto == null) {
			return null;
		}

		switch (dto.getClass().getSimpleName()) {
		case "NewOrderDto":
			Order order = new Order();
			NewOrderDto newDto = (NewOrderDto) dto;
			order.setDate(newDto.getDate());
			order.setDeliveryAddress(newDto.getDeliveryAddress());
			order.setDeliveryStatus(DeliveryStatus.valueOf(newDto.getDeliveryStatus()));
			order.setTotalPrice(newDto.getTotalPrice());
			
			// set customer and dish also
			Customer customer = customerRepository.findById(newDto.getCustomerId())
					.orElseThrow(()->new RuntimeException("Customer does not exist!"));
			Dish dish = dishRepository.findById(newDto.getDishId())
					.orElseThrow(()->new RuntimeException("Dish does not exist!"));
			
			order.setCustomer(customer);
			order.setDish(dish);
			
			return order;
			
		default:
			throw new IllegalArgumentException("Unsupported DTO: " + dto.getClass().getSimpleName());
		}
	}

	public <T> T toDto(Order order, Class<T> dtoClass) {
		if (order == null) {
			return null;
		}

		T dto;

		try {
			dto = dtoClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to create DTO instance", e);
		}

		switch (dtoClass.getSimpleName()) {
		
		case "OrderDto":
			OrderDto orderDto = new OrderDto();
			orderDto.setId(order.getId());
			orderDto.setDate(order.getDate());
			orderDto.setDeliveryAddress(order.getDeliveryAddress());
			orderDto.setDeliveryStatus(order.getDeliveryStatus());
			orderDto.setTotalPrice(order.getTotalPrice());
			
			return dtoClass.cast(orderDto);
			
		case "OrderDetailsDto":
			OrderDetailsDto orderDetailsDto = new OrderDetailsDto();
			orderDetailsDto.setId(order.getId());
			orderDetailsDto.setDate(order.getDate());
			orderDetailsDto.setDeliveryAddress(order.getDeliveryAddress());
			orderDetailsDto.setDeliveryStatus(order.getDeliveryStatus());
			orderDetailsDto.setTotalPrice(order.getTotalPrice());
			
			// set customer and dish dtos also
			Customer customer = order.getCustomer();
			Dish dish = order.getDish();
			
			CustomerDto customerDto = customerConverter.toDto(customer, CustomerDto.class);
			DishDto dishDto = dishConverter.toDto(dish, DishDto.class);
			
			orderDetailsDto.setCustomer(customerDto);
			orderDetailsDto.setDish(dishDto);
			
			return dtoClass.cast(orderDetailsDto);
			
		default:
			throw new IllegalArgumentException("Unsupported DTO: " + dtoClass.getSimpleName());
		}
	}
}
