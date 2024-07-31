package dev.bhardwaj.food_order.service;

import java.util.List;

import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.entity.Review;

public interface CustomerService {
	// requirement
	void login();
	Customer getCustomerDetails(long id);
	Customer updateCustomerDetails(); // pass update dto
	void deleteCustomerAccount(long id);
	void addRating();
	void addReview();
	
	// other
	void createCustomer();
	List<Rating> getRatingsGiven();
	List<Review> getReviewsGiven();
	
}
