package dev.bhardwaj.food_order.service;

import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.CreateReviewDto;
import dev.bhardwaj.food_order.dto.DtoToEntityMapper;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Review;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.DishRepository;
import dev.bhardwaj.food_order.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final CustomerRepository customerRepository;
	private final DishRepository dishRepository;
	private final DtoToEntityMapper dtoToEntityMapper;
	
	
	public ReviewServiceImpl(ReviewRepository reviewRepository,
			CustomerRepository customerRepository,
			DishRepository dishRepository,
			DtoToEntityMapper dtoToEntityMapper) {
		this.reviewRepository = reviewRepository;
		this.customerRepository = customerRepository;
		this.dishRepository = dishRepository;
		this.dtoToEntityMapper = dtoToEntityMapper;
	}
	@Override
	public Review createReview(CreateReviewDto reviewDto) {
		Review review = dtoToEntityMapper.reviewDtoToEntity(reviewDto);
		
		// save this in respective customer reviews list and dish reviews list
		// save this rating for the customer and dish
		Customer customer = customerRepository.findById(reviewDto.getCustomerId())
				.orElseThrow(()->new RuntimeException("Customer does not exist"));
		customer.getReviewsGiven().add(review);
		customerRepository.save(customer);
		
		Dish dish = dishRepository.findById(reviewDto.getDishId())
				.orElseThrow(()->new RuntimeException("Dish does not exist"));
		dish.getReveiws().add(review);
		dishRepository.save(dish);
		
		// save and return the review
		return reviewRepository.save(review);
	}

	@Override
	public void deleteReview(long reviewId) {
		// test that deleting rating should also delete this from the respective customer ratings list and dish ratings list
		reviewRepository.deleteById(reviewId);
	}

}
