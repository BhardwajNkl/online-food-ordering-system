package dev.bhardwaj.food_order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.CreateOrderDto;
import dev.bhardwaj.food_order.dto.DtoToEntityMapper;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
	private final DtoToEntityMapper dtoToEntityMapper;
	
	public OrderServiceImpl(OrderRepository orderRepository, DtoToEntityMapper dtoToEntityMapper) {
		this.orderRepository = orderRepository;
		this.dtoToEntityMapper = dtoToEntityMapper;
	}

	@Override
	public Order placeOrder(CreateOrderDto orderDto) {
		Order order = dtoToEntityMapper.orderDtoToEntity(orderDto);
		return orderRepository.save(order);
	}

	@Override
	public Order getOrderDetails(long orderId) {
		return orderRepository.findById(orderId)
				.orElseThrow(()->new RuntimeException("Order does not exist!"));
	}

	@Override
	public void getBill(long orderId) {
		
	}

// NO NEED OF THIS HERE
//	@Override
//	public List<Order> getOrdersForCustomer(long customerId) {
//		return null;
//	}
//
	
	@Override
	public List<Order> getOrdersForRestaurant(int restaurantId) {
		return orderRepository.findAll()
		.stream()
		.filter(order->{return order.getDish().getRestaurant().getId()==restaurantId;})
		.collect(Collectors.toList());
	}
	
}
