package dev.bhardwaj.food_order.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

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
import dev.bhardwaj.food_order.exception.DoesNotExistException;
import dev.bhardwaj.food_order.exception.DtoEntityConversionException;
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
			order.setDeliveryStatus(DeliveryStatus.valueOf("ORDER_RECEIVED"));			
			Customer customer = customerRepository.findById(newDto.getCustomerId())
					.orElseThrow(()->new DoesNotExistException("Customer does not exist!"));
			
			List<Dish> dishes = dishRepository.findAllById(newDto.getDishIds());
			
			// set total order price
			float totalPrice = 0;
			for(Dish dish: dishes) {
				totalPrice+=dish.getPrice();
			}
			
			order.setTotalPrice(totalPrice);
			
			order.setCustomer(customer);
			order.setDishes(dishes);
			
			return order;
			
		default:
			throw new DtoEntityConversionException("DTO to Order Entity conversion failed");
		}
	}

	public <T> T toDto(Order order, Class<T> dtoClass) {
		if (order == null) {
			return null;
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
			
			Customer customer = order.getCustomer();
			List<Dish> dishes = order.getDishes();
			
			CustomerDto customerDto = customerConverter.toDto(customer, CustomerDto.class);
			List<DishDto> dishDtos = dishes.stream()
					.map( dish-> dishConverter.toDto(dish, DishDto.class))
					.collect(Collectors.toList());
			
			orderDetailsDto.setCustomer(customerDto);
			orderDetailsDto.setDishes(dishDtos);
			
			return dtoClass.cast(orderDetailsDto);
			
		default:
			throw new DtoEntityConversionException("Order Entity to DTO conversion failed");
		}
	}
}
