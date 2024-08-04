package dev.bhardwaj.food_order.service;

import dev.bhardwaj.food_order.dto.DtoToEntityMapper;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.CreateCustomerDto;
import dev.bhardwaj.food_order.dto.CreateOrderDto;
import dev.bhardwaj.food_order.dto.CreateRatingDto;
import dev.bhardwaj.food_order.dto.CreateReviewDto;
import dev.bhardwaj.food_order.dto.UpdateCustomerDto;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Customer.Address;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.entity.Rating;
import dev.bhardwaj.food_order.entity.Review;
import dev.bhardwaj.food_order.repository.CustomerRepository;

@Service
public class CustomerServiceImpl implements CustomerService {
	
	private final CustomerRepository customerRepository;
	private final OrderService orderService;
	private final RatingService ratingService;
	private final ReviewService reviewService;
	
	private final DtoToEntityMapper dtoToEntityMapper;
	
	public CustomerServiceImpl(CustomerRepository customerRepository,
			OrderService orderService,
			RatingService ratingService,
			ReviewService reviewService,
			DtoToEntityMapper dtoToEntityMapper) {
		this.customerRepository = customerRepository;
		this.orderService = orderService;
		this.ratingService = ratingService;
		this.reviewService = reviewService;
		this.dtoToEntityMapper = dtoToEntityMapper;
	}

	@Override
	public Customer createCustomer(CreateCustomerDto customerDto) {
		Customer customer =dtoToEntityMapper.customerDtoToEntity(customerDto);
		return customerRepository.save(customer);
	}

	@Override
	public Customer updateCustomerDetails(UpdateCustomerDto customerDto) {
		Customer customer = customerRepository.findById(customerDto.getId())
				.orElseThrow(()->new RuntimeException("Customer doe not exist!"));
		
		customer.setName(customerDto.getName());
		customer.setEmail(customer.getEmail());
		Address address = customer.getAddress();
		address.setLocality(customerDto.getLocality());
		address.setCity(customerDto.getCity());
		address.setState(customerDto.getState());
		address.setPinCode(customerDto.getPinCode());
		customer.setAddress(address);
		return customerRepository.save(customer);
	}

	@Override
	public void deleteCustomer(long customerId) {
		customerRepository.deleteById(customerId);
	}

	@Override
	public Customer getCustomerDetails(long customerId) {
		return customerRepository.findById(customerId)
				.orElseThrow(()->new RuntimeException("Customer doe not exist!"));
	}

	@Override
	public void login() {
		
	}

	@Override
	public Order placeOrder(CreateOrderDto orderDto) {
		return orderService.placeOrder(orderDto);
	}


	@Override
	public Rating addRating(CreateRatingDto ratingDto) {
		return ratingService.createRating(ratingDto);
	}

	@Override
	public Review addReview(CreateReviewDto reviwDto) {
		return reviewService.createReview(reviwDto);
	}

	@Override
	public List<Order> getOrdersForCustomer(long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()->new RuntimeException("Customer doe not exist!"));
		return customer.getOrders();
	}

}
