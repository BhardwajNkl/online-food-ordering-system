package dev.bhardwaj.food_order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.NewOrderDto;
import dev.bhardwaj.food_order.dto.OrderDetailsDto;
import dev.bhardwaj.food_order.dto.OrderDto;
import dev.bhardwaj.food_order.dto.converter.OrderConverter;
import dev.bhardwaj.food_order.dto.BillDto;
import dev.bhardwaj.food_order.dto.DtoToEntityMapper;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
	
	private final OrderRepository orderRepository;
	private final CustomerService customerService;
	private final CustomerRepository customerRepository;
	private final DtoToEntityMapper dtoToEntityMapper;
	
	@Autowired
	private OrderConverter orderConverter;
	
	public OrderServiceImpl(OrderRepository orderRepository,
			CustomerService customerService,
			CustomerRepository customerRepository,
			DtoToEntityMapper dtoToEntityMapper) {
		this.orderRepository = orderRepository;
		this.customerService = customerService;
		this.customerRepository = customerRepository;
		this.dtoToEntityMapper = dtoToEntityMapper;
	}

	@Override
	public OrderDto placeOrder(NewOrderDto orderDto) {
//		Order order = dtoToEntityMapper.newOrderDtoToEntity(orderDto);
		Order order = orderConverter.toEntity(orderDto);
		Order createdOrder = orderRepository.save(order);
		return orderConverter.toDto(createdOrder, OrderDto.class);
	}

	@Override
	public OrderDetailsDto getOrderDetails(long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(()->new RuntimeException("Order does not exist!"));
		return orderConverter.toDto(order, OrderDetailsDto.class);
	}
	
	@Override
	public List<OrderDto> getOrdersForRestaurant(int restaurantId) {
		// TRY OPTIMIZE
		return orderRepository.findAll()
		.stream()
		.filter(order->{return order.getDish().getRestaurant().getId()==restaurantId;})
		.map(
			order ->{
				return orderConverter.toDto(order, OrderDto.class);
			}
			)
		.collect(Collectors.toList());
	}

	@Override
	public List<OrderDto> getOrdersForCustomer(long customerId) {
		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(()->new RuntimeException("Customer does not exist"));
		return customer.getOrders()
				.stream()
				.map(
					order->{
						return orderConverter.toDto(order, OrderDto.class);
					}
					)
				.collect(Collectors.toList());
	}
	
	@Override
	public BillDto getBill(long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(()->new RuntimeException("Order does not exist!"));
		
		BillDto billDto = new BillDto();
		billDto.setOrderId(order.getId());
		billDto.setDate(order.getDate());
		billDto.setTotalPrice(order.getTotalPrice());
		billDto.setDeliveryAddress(order.getDeliveryAddress());
		billDto.setCustomerName(order.getCustomer().getName());
		billDto.setDishName(order.getDish().getName());
		
		return billDto;
	}
	
}
