package dev.bhardwaj.food_order.dto;

import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Customer.Address;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Dish.Cuisine;
import dev.bhardwaj.food_order.entity.Order.DeliveryStatus;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.entity.Restaurant;
import dev.bhardwaj.food_order.entity.Review;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.DishRepository;
import dev.bhardwaj.food_order.repository.RestaurantRepository;

@Component
public class DtoToEntityMapper {
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	private DishRepository dishRepository;
	

	public Restaurant restaurantDtoToEntity(CreateRestaurantDto restaurantDto) {
		Function<CreateRestaurantDto, Restaurant> restaurantDtoToEntity = (dto) -> {
			Restaurant restaurant = new Restaurant();
			restaurant.setName(dto.getName());
			restaurant.setOpen(dto.isOpen());
			return restaurant;
		};
		return restaurantDtoToEntity.apply(restaurantDto);
	}

	public Dish dishDtoToEntity(CreateDishDto dishDto) {
		Restaurant restaurant = restaurantRepository.findById(dishDto.getRestaurantId())
				.orElseThrow(()->new RuntimeException("Restaurant does not exist!"));
		
		Function<CreateDishDto, Dish> dishDtoToEntity = (dto) -> {
			Dish dish = new Dish();
			dish.setName(dto.getName());
			dish.setDescription(dto.getDescription());
			dish.setPrice(dto.getPrice());
			dish.setRestaurant(restaurant);
			dish.setAvaialability(true);
			dish.setCuisine(Cuisine.valueOf(dto.getCuisine()));
			return dish;
		};
		return dishDtoToEntity.apply(dishDto);
	}
	
	public Customer customerDtoToEntity(CreateCustomerDto customerDto) {
		Function<CreateCustomerDto, Customer> customerDtoToEntity = (dto) -> {
			Customer customer = new Customer();
			customer.setName(dto.getName());
			customer.setEmail(dto.getEmail());
			Address address = customer.new Address(dto.getLocality(),dto.getCity(),dto.getState(),dto.getPinCode());
			customer.setAddress(address);
			return customer;
		};
		return customerDtoToEntity.apply(customerDto);
	}
	
	public Order orderDtoToEntity(CreateOrderDto orderDto) {
		Function<CreateOrderDto, Order> orderDtoToEntity = (dto) -> {
			Order order = new Order();
			Customer customer = customerRepository.findById(dto.getCustomerId())
					.orElseThrow(()->new RuntimeException("Customer does not exist!"));
			Dish dish = dishRepository.findById(dto.getDishId())
					.orElseThrow(()->new RuntimeException("Dish does not exist!"));
			
			order.setCustomer(customer);
			order.setDish(dish);
			order.setDate(dto.getDate());
			order.setDeliveryAddress(dto.getDeliveryAddress());
			order.setTotalPrice(dto.getTotalPrice());
			order.setDeliveryStatus(DeliveryStatus.valueOf(dto.getDeliveryStatus()));
			return order;
		};
		return orderDtoToEntity.apply(orderDto);
	}
	
	public Dish updateDishDtoToEntity(UpdateDishDto dishDto) {
		Function<UpdateDishDto, Dish> updateDishDtoToEntity = (dto) -> {
			Dish dish = dishRepository.findById(dto.getId())
					.orElseThrow(()->new RuntimeException("Dish does not exist!"));
			dish.setName(dto.getName());
			dish.setDescription(dto.getDescription());
			dish.setPrice(dto.getPrice());
			dish.setAvaialability(dto.isAvaialability());
			dish.setCuisine(Cuisine.valueOf(dto.getCuisine()));
			return dish;
		};
		return updateDishDtoToEntity.apply(dishDto);
	}
	
	public Rating ratingDtoToEntity(CreateRatingDto ratingDto) {
		Function<CreateRatingDto, Rating> ratingDtoToEntity = (dto) -> {
			Rating rating = new Rating();
			rating.setCustomerId(dto.getCustomerId());
			rating.setDishId(dto.getDishId());
			rating.setRating(dto.getRating());
			rating.setTimeStamp(dto.getTimeStamp());
			return rating;
		};
		return ratingDtoToEntity.apply(ratingDto);
	}
	
	public Review reviewDtoToEntity(CreateReviewDto reviewDto) {
		Function<CreateReviewDto, Review> reviewDtoToEntity = (dto) -> {
			Review review = new Review();
			review.setCustomerId(dto.getCustomerId());
			review.setDishId(dto.getDishId());
			review.setReview(dto.getReview());
			review.setTimestamp(dto.getTimestamp());
			return review;
		};
		return reviewDtoToEntity.apply(reviewDto);
	}
	
	
}
