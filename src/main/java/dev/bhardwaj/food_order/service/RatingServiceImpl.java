package dev.bhardwaj.food_order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.NewRatingDto;
import dev.bhardwaj.food_order.dto.RatingDetailsDto;
import dev.bhardwaj.food_order.dto.RatingDto;
import dev.bhardwaj.food_order.dto.converter.RatingConverter;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.exception.DoesNotExistException;
import dev.bhardwaj.food_order.exception.NotAllowedException;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.DishRepository;
import dev.bhardwaj.food_order.repository.RatingRepository;

@Service
public class RatingServiceImpl implements RatingService {
	
	private final RatingRepository ratingRepository;
	private final CustomerRepository customerRepository;
	private final DishRepository dishRepository;
	
	@Autowired
	private RatingConverter ratingConverter;
	
	public RatingServiceImpl(RatingRepository ratingRepository,
			CustomerRepository customerRepository,
			DishRepository dishRepository) {
		this.ratingRepository = ratingRepository;
		this.customerRepository = customerRepository;
		this.dishRepository = dishRepository;
	}

	@Override
	public RatingDto createRating(NewRatingDto ratingDto) {
		
		// note: rating can be added to ordered dishes only
		Customer customer = customerRepository.findById(ratingDto.getCustomerId())
				.orElseThrow(()->new DoesNotExistException("Customer with given id does not exist"));
		
		boolean customerHasOrderedThisDish = customer.getOrders()
		.stream()
		.anyMatch(order->order.getDish().getId()==ratingDto.getDishId());
		
		if(!customerHasOrderedThisDish) {
			throw new NotAllowedException("Cannot give rating! Customer has not ordered this dish!");
		}
		
		
		Rating rating = ratingConverter.toEntity(ratingDto);
		
		Rating savedRating = ratingRepository.save(rating);
		
		customer.getRatingsGiven().add(rating);
		
		customerRepository.save(customer);
		
		// add the rating to dish ratings list
		Dish dish = dishRepository.findById(ratingDto.getDishId())
				.orElseThrow(()->new RuntimeException("Dish does not exist"));
		
		long currentRatingCount = dish.getRatings().size();
		float currentAverageRating = dish.getAverageRating();
		float newAverageRating = ((currentAverageRating*currentRatingCount) + rating.getRating())/(currentRatingCount+1);
		dish.setAverageRating(newAverageRating);
		
		dish.getRatings().add(rating);
		
		dishRepository.save(dish);
				
		return ratingConverter.toDto(savedRating, RatingDto.class);
	}

	@Override
	public List<RatingDetailsDto> getRatingsGivenByCustomer(long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()->new DoesNotExistException("Customer with given id does not exist"));
		return customer.getRatingsGiven().stream()
				.map(rating->ratingConverter.toDto(rating, RatingDetailsDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<RatingDetailsDto> getRatingsForDish(int dishId) {
		Dish dish = dishRepository.findById(dishId)
				.orElseThrow(()->new DoesNotExistException("Dish with given id does not exist"));
		return dish.getRatings()
				.stream()
				.map(rating->ratingConverter.toDto(rating, RatingDetailsDto.class))
				.collect(Collectors.toList());
	}

}
