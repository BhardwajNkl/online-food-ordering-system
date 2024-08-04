package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.dto.CreateCustomerDto;
import dev.bhardwaj.food_order.dto.CreateOrderDto;
import dev.bhardwaj.food_order.dto.CreateRatingDto;
import dev.bhardwaj.food_order.dto.CreateReviewDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.entity.Review;

public interface CustomerService {
	Customer createCustomer(CreateCustomerDto customerDto);
	Customer updateCustomerDetails(UpdateCustomerDto customerDto);
	void deleteCustomer(long id);
	Customer getCustomerDetails(long id);
	
	void login();
	
	Order placeOrder(CreateOrderDto orderDto);
	List<Order> getOrdersForCustomer(long customerId);
	
	Rating addRating(CreateRatingDto ratingDto);
	Review addReview(CreateReviewDto reviewDto);
		
//	List<Rating> getRatingsGiven();
//	List<Review> getReviewsGiven();
	
	
	
	
}
