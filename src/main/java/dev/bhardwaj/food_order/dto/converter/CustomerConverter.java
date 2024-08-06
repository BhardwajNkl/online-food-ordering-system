package dev.bhardwaj.food_order.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.dto.CustomerDetailsDto;
import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.DishDto;
import dev.bhardwaj.food_order.dto.NewCustomerDto;
import dev.bhardwaj.food_order.dto.OrderDetailsDto;
import dev.bhardwaj.food_order.dto.OrderDto;
import dev.bhardwaj.food_order.dto.RatingDetailsDto;
import dev.bhardwaj.food_order.dto.ReviewDetailsDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.DishRepository;

@Component
public class CustomerConverter {
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private DishRepository dishRepository;
	
	@Autowired
	private DishConverter dishConverter;
	
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
					.orElseThrow(()->new RuntimeException("Customer does not exist!"));
			customerToUpdate.setName(updateDto.getName());
			customerToUpdate.setEmail(updateDto.getEmail());
			Customer.Address addressToUpdate = new Customer.Address(updateDto.getLocality(),
					updateDto.getCity(),updateDto.getState(),updateDto.getPinCode());
			customerToUpdate.setAddress(addressToUpdate);
			return customerToUpdate;
			
		default:
			throw new IllegalArgumentException("Unsupported DTO: " + dto.getClass().getSimpleName());
		}
	}

	public <T> T toDto(Customer customer, Class<T> dtoClass) {
		if (customer == null) {
			return null;
		}

		T dto;

		try {
			dto = dtoClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to create DTO instance", e);
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
			
//			List<RatingDetailsDto> ratings = customer.getRatingsGiven()
//					.stream()
//					.map(
//						rating->{
//							RatingDetailsDto ratingDetailsDto = new RatingDetailsDto();
//							ratingDetailsDto.setId(rating.getId());
//							ratingDetailsDto.setCustomerId(rating.getCustomerId());
//							ratingDetailsDto.setDishId(rating.getDishId());
//							ratingDetailsDto.setRating(rating.getRating());
//							ratingDetailsDto.setTimeStamp(rating.getTimeStamp());
//							
////							Customer customer = customerRepository
////									.findById(rating.getCustomerId())
////									.orElseThrow(()->new RuntimeException("Customer does not exist!"));
//							Dish dishRated = dishRepository.findById(rating.getDishId())
//									.orElseThrow(()->new RuntimeException("Dish does not exist!"));
//							
//							// convert to dto
//							ratingDetailsDto.setCustomerDto(toDto(customer, CustomerDto.class));
//							ratingDetailsDto.setDishDto(dishConverter.toDto(dishRated, DishDto.class));
//							
//							return ratingDetailsDto;
//						}
//						)
//					.collect(Collectors.toList());
//			
//			customerDetailsDto.setRatingsGiven(ratings);
			
//			List<ReviewDetailsDto> reviews = customer.getReviewsGiven()
//					.stream()
//					.map(
//						review->{
//							ReviewDetailsDto reviewDetailsDto = new ReviewDetailsDto();
//							reviewDetailsDto.setId(review.getId());
//							reviewDetailsDto.setCustomerId(review.getCustomerId());
//							reviewDetailsDto.setDishId(review.getDishId());
//							reviewDetailsDto.setReview(review.getReview());
//							reviewDetailsDto.setTimeStamp(review.getTimestamp());
//							
////							Customer customer = customerRepository
////									.findById(review.getCustomerId())
////									.orElseThrow(()->new RuntimeException("Customer does not exist!"));
//							Dish dishReviewd = dishRepository.findById(review.getDishId())
//									.orElseThrow(()->new RuntimeException("Dish does not exist!"));
//							
//							// convert to dto
//							reviewDetailsDto.setCustomerDto(toDto(customer, CustomerDto.class));
//							reviewDetailsDto.setDishDto(dishConverter.toDto(dishReviewd, DishDto.class));
//							
//							return reviewDetailsDto;
//						}
//						)
//					.collect(Collectors.toList());
//			
//			customerDetailsDto.setReviewsGiven(reviews);
			
			return dtoClass.cast(customerDetailsDto);
			
		default:
			throw new IllegalArgumentException("Unsupported DTO: " + dtoClass.getSimpleName());
		}

	}
}
