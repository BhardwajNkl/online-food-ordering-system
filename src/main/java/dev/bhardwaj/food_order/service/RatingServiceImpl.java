package dev.bhardwaj.food_order.service;

import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.CreateRatingDto;
import dev.bhardwaj.food_order.dto.DtoToEntityMapper;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.DishRepository;
import dev.bhardwaj.food_order.repository.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService {
	
	private final RatingRepository ratingRepository;
	private final CustomerRepository customerRepository;
	private final DishRepository dishRepository;
	private final DtoToEntityMapper dtoToEntityMapper;
	
	public RatingServiceImpl(RatingRepository ratingRepository,
			CustomerRepository customerRepository,
			DishRepository dishRepository,
			DtoToEntityMapper dtoToEntityMapper) {
		this.ratingRepository = ratingRepository;
		this.customerRepository = customerRepository;
		this.dishRepository = dishRepository;
		this.dtoToEntityMapper = dtoToEntityMapper;
	}

	@Override
	public Rating createRating(CreateRatingDto ratingDto) {
		Rating rating = dtoToEntityMapper.ratingDtoToEntity(ratingDto);
		// save this rating for the customer and dish
		Customer customer = customerRepository.findById(ratingDto.getCustomerId())
				.orElseThrow(()->new RuntimeException("Customer does not exist"));
		customer.getRatingsGiven().add(rating);
		customerRepository.save(customer);
		
		Dish dish = dishRepository.findById(ratingDto.getDishId())
				.orElseThrow(()->new RuntimeException("Dish does not exist"));
		dish.getRatings().add(rating);
		dishRepository.save(dish);
		
		
		// now save this rating and return
		return ratingRepository.save(rating);
	}

	@Override
	public void deleteRating(long ratingId) {
		// test that deleting rating should also delete this from the respective customer ratings list and dish ratings list
		ratingRepository.deleteById(ratingId);
	}

}
