package dev.bhardwaj.food_order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.NewReviewDto;
import dev.bhardwaj.food_order.dto.ReviewDetailsDto;
import dev.bhardwaj.food_order.dto.ReviewDto;
import dev.bhardwaj.food_order.dto.converter.ReviewConverter;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Dish;
import dev.bhardwaj.food_order.entity.Review;
import dev.bhardwaj.food_order.exception.DoesNotExistException;
import dev.bhardwaj.food_order.exception.NotAllowedException;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.DishRepository;
import dev.bhardwaj.food_order.repository.ReviewRepository;

@Service
public class ReviewServiceImpl implements ReviewService {

	private final ReviewRepository reviewRepository;
	private final CustomerRepository customerRepository;
	private final DishRepository dishRepository;

	@Autowired
	private ReviewConverter reviewConverter;

	public ReviewServiceImpl(ReviewRepository reviewRepository, CustomerRepository customerRepository,
			DishRepository dishRepository) {
		this.reviewRepository = reviewRepository;
		this.customerRepository = customerRepository;
		this.dishRepository = dishRepository;
	}

	@Override
	public ReviewDto createReview(NewReviewDto reviewDto) {
		// review can be added to ordered dishes only
		Customer customer = customerRepository.findById(reviewDto.getCustomerId())
				.orElseThrow(() -> new DoesNotExistException("Customer with given id does not exist"));

		boolean customerHasOrderedThisDish = customer.getOrders().stream()
				.anyMatch(order -> order.getDish().getId() == reviewDto.getDishId());

		if (!customerHasOrderedThisDish) {
			throw new NotAllowedException("Cannot give review! Customer has not ordered this dish!");
		}

		Review review = reviewConverter.toEntity(reviewDto);
		Review savedReview = reviewRepository.save(review);

		// add this review to customer's reviews list
		customer.getReviewsGiven().add(review);
		customerRepository.save(customer);

		// add the rating to dish' reviews list
		Dish dish = dishRepository.findById(reviewDto.getDishId())
				.orElseThrow(() -> new DoesNotExistException("Dish with given id does not exist"));
		dish.getReveiws().add(review);

		dishRepository.save(dish);

		return reviewConverter.toDto(savedReview, ReviewDto.class);
	}

	@Override
	public List<ReviewDetailsDto> getReviewsGivenByCustomer(long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new DoesNotExistException("Customer with given id does not exist"));

		return customer.getReviewsGiven().stream().map(review -> reviewConverter.toDto(review, ReviewDetailsDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public List<ReviewDetailsDto> getReviewsForDish(int dishId) {
		Dish dish = dishRepository.findById(dishId).orElseThrow(() -> new DoesNotExistException("Dish with given id does not exist"));
		return dish.getReveiws().stream().map(review -> reviewConverter.toDto(review, ReviewDetailsDto.class))
				.collect(Collectors.toList());
	}

}
