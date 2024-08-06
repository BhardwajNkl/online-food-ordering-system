package dev.bhardwaj.food_order.dto.converter;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import dev.bhardwaj.food_order.dto.CustomerDto;
import dev.bhardwaj.food_order.dto.DishDetailsDto;
import dev.bhardwaj.food_order.dto.DishDto;
import dev.bhardwaj.food_order.dto.NewDishDto;
import dev.bhardwaj.food_order.dto.OrderDetailsDto;
import dev.bhardwaj.food_order.dto.RatingDetailsDto;
import dev.bhardwaj.food_order.dto.RestaurantDto;
import dev.bhardwaj.food_order.dto.ReviewDetailsDto;
import dev.bhardwaj.food_order.dto.UpdateDishDto;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Dish.Cuisine;
import dev.bhardwaj.food_order.entity.Restaurant;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.DishRepository;
import dev.bhardwaj.food_order.repository.RestaurantRepository;
import dev.bhardwaj.food_order.service.DishService;
import dev.bhardwaj.food_order.service.RestaurantService;

@Component
public class DishConverter {
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private DishRepository dishRepository;
	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
	@Lazy
	private RestaurantConverter restaurantConverter;
	
	@Autowired
	@Lazy
	private CustomerConverter customerConverter;
	
//	@Autowired
//	private DishConverter dishConverter;
	
	public Dish toEntity(Object dto) {
		if (dto == null) {
			return null;
		}

		switch (dto.getClass().getSimpleName()) {
		case "NewDishDto":
			Dish dish = new Dish();
			NewDishDto newDto = (NewDishDto) dto;
			Restaurant restaurant = restaurantRepository.findById(newDto.getRestaurantId())
					.orElseThrow(()->new RuntimeException("Restaurant does not exist!"));
			dish.setName(newDto.getName());
			dish.setAvailable(newDto.isAvailable());
			dish.setAverageRating(0); // 0 indicates not yet rated
			dish.setCuisine(Cuisine.valueOf(newDto.getCuisine()));
			dish.setDescription(newDto.getDescription());
			dish.setPrice(newDto.getPrice());
			dish.setRestaurant(restaurant);			
			return dish;
			
		case "UpdateDishDto":
			UpdateDishDto updateDto = (UpdateDishDto) dto;
			Dish dishToUpdate = dishRepository.findById(updateDto.getId())
					.orElseThrow(()->new RuntimeException("Dish does not exist!"));
			dishToUpdate.setName(updateDto.getName());
			dishToUpdate.setDescription(updateDto.getDescription());
			dishToUpdate.setPrice(updateDto.getPrice());
			dishToUpdate.setAvailable(updateDto.isAvailable());
			dishToUpdate.setCuisine(Cuisine.valueOf(updateDto.getCuisine()));
			
			return dishToUpdate;
			
		default:
			throw new IllegalArgumentException("Unsupported DTO: " + dto.getClass().getSimpleName());
		}
	}

	public <T> T toDto(Dish dish, Class<T> dtoClass) {
		if (dish == null) {
			return null;
		}

		T dto;

		try {
			dto = dtoClass.getDeclaredConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Failed to create DTO instance", e);
		}

		switch (dtoClass.getSimpleName()) {
		
		case "DishDto":
			DishDto dishDto = new DishDto();
			dishDto.setId(dish.getId());
			dishDto.setAvailable(dish.isAvailable());
			dishDto.setAverageRating(dish.getAverageRating());
			dishDto.setCuisine(dish.getCuisine());
			dishDto.setDescription(dish.getDescription());
			dishDto.setName(dish.getName());
			dishDto.setPrice(dish.getPrice());
			
			return dtoClass.cast(dishDto);
			
		case "DishDetailsDto":
			DishDetailsDto dishDetailsDto = new DishDetailsDto();
			dishDetailsDto.setId(dish.getId());
			dishDetailsDto.setAvailable(dish.isAvailable());
			dishDetailsDto.setAverageRating(dish.getAverageRating());
			dishDetailsDto.setCuisine(dish.getCuisine());
			dishDetailsDto.setDescription(dish.getDescription());
			dishDetailsDto.setName(dish.getName());
			dishDetailsDto.setPrice(dish.getPrice());
			
//			List<OrderDetailsDto> orders = dish.getOrders()
//					.stream()
//					.map(
//						order->{
//							return new OrderDetailsDto();
//						}
//						)
//					.collect(Collectors.toList());
//			
//			dishDetailsDto.setOrders(orders);
			
//			List<RatingDetailsDto> ratings = dish.getRatings()
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
//							Customer customer = customerRepository
//									.findById(rating.getCustomerId())
//									.orElseThrow(()->new RuntimeException("Customer does not exist!"));
//							Dish dishRated = dishRepository.findById(rating.getDishId())
//									.orElseThrow(()->new RuntimeException("Dish does not exist!"));
//							
//							// convert to dto
//							ratingDetailsDto.setCustomerDto(customerConverter.toDto(customer, CustomerDto.class));
//							ratingDetailsDto.setDishDto(toDto(dishRated, DishDto.class));
//							
//							return ratingDetailsDto;
//						}
//						)
//					.collect(Collectors.toList());
//			
//			dishDetailsDto.setRatings(ratings);
			
			List<ReviewDetailsDto> reviews = dish.getReveiws()
					.stream()
					.map(
						review->{
							ReviewDetailsDto reviewDetailsDto = new ReviewDetailsDto();
							reviewDetailsDto.setId(review.getId());
							reviewDetailsDto.setCustomerId(review.getCustomerId());
							reviewDetailsDto.setDishId(review.getDishId());
							reviewDetailsDto.setReview(review.getReview());
							reviewDetailsDto.setTimeStamp(review.getTimestamp());
							
							Customer customer = customerRepository
									.findById(review.getCustomerId())
									.orElseThrow(()->new RuntimeException("Customer does not exist!"));
							Dish dishReviewd = dishRepository.findById(review.getDishId())
									.orElseThrow(()->new RuntimeException("Dish does not exist!"));
							
							// convert to dto
							reviewDetailsDto.setCustomerDto(customerConverter.toDto(customer, CustomerDto.class));
							reviewDetailsDto.setDishDto(toDto(dishReviewd, DishDto.class));
							
							return reviewDetailsDto;
						}
						)
					.collect(Collectors.toList());
			
			dishDetailsDto.setReveiws(reviews);
			
			RestaurantDto restaurantDto = restaurantConverter.toDto(dish.getRestaurant(), RestaurantDto.class);
			dishDetailsDto.setRestaurant(restaurantDto);
			
			return dtoClass.cast(dishDetailsDto);
			
		default:
			throw new IllegalArgumentException("Unsupported DTO: " + dtoClass.getSimpleName());
		}
	}
}
