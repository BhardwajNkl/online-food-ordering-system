package dev.bhardwaj.food_order.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import dev.bhardwaj.food_order.dto.NewOrderDto;
import dev.bhardwaj.food_order.dto.OrderDetailsDto;
import dev.bhardwaj.food_order.dto.OrderDto;
import dev.bhardwaj.food_order.dto.converter.OrderConverter;
import dev.bhardwaj.food_order.dto.BillDto;
import dev.bhardwaj.food_order.dto.DtoToEntityMapper;
import dev.bhardwaj.food_order.entity.Customer;
import dev.bhardwaj.food_order.entity.Order;
import dev.bhardwaj.food_order.entity.Order.DeliveryStatus;
import dev.bhardwaj.food_order.exception.NotAllowedException;
import dev.bhardwaj.food_order.repository.CustomerRepository;
import dev.bhardwaj.food_order.repository.OrderRepository;
import dev.bhardwaj.food_order.security.SecurityUser;

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
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityUser loggedUser = (SecurityUser) authentication.getPrincipal();
		Order order = orderRepository.findById(orderId)
				.orElseThrow(()->new RuntimeException("Order does not exist!"));
		
		if (loggedUser.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN_USER"))) {
	        // admins can access any order details
			return orderConverter.toDto(order, OrderDetailsDto.class);
	    } else {
	        // for customers, we check if the order was made by this particular customer
	        if (loggedUser.getUser().getCustomer().getId() == order.getCustomer().getId()) {
				return orderConverter.toDto(order, OrderDetailsDto.class);

	        } else {
	            throw new NotAllowedException("You can only access your own order details");
	        }
	    }
		
		
//		if(loggedUser.getUser().getCustomer().getId()==order.getCustomer().getId()) {
//			return orderConverter.toDto(order, OrderDetailsDto.class);
//		} else {
//			throw new NotAllowedException("You can get order details of your own orders only");
//		}
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
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		SecurityUser loggedUser = (SecurityUser) authentication.getPrincipal();
		Order order = orderRepository.findById(orderId)
				.orElseThrow(()->new RuntimeException("Order does not exist!"));
		
		if(loggedUser.getUser().getCustomer().getId()==order.getCustomer().getId()) {
			BillDto billDto = new BillDto();
			billDto.setOrderId(order.getId());
			billDto.setDate(order.getDate());
			billDto.setTotalPrice(order.getTotalPrice());
			billDto.setDeliveryAddress(order.getDeliveryAddress());
			billDto.setCustomerName(order.getCustomer().getName());
			billDto.setDishName(order.getDish().getName());
			
			return billDto;
		} else {
			throw new NotAllowedException("You can get bill of your own orders only");
		}
		
		// need check
//		Order order = orderRepository.findById(orderId)
//				.orElseThrow(()->new RuntimeException("Order does not exist!"));
		
//		BillDto billDto = new BillDto();
//		billDto.setOrderId(order.getId());
//		billDto.setDate(order.getDate());
//		billDto.setTotalPrice(order.getTotalPrice());
//		billDto.setDeliveryAddress(order.getDeliveryAddress());
//		billDto.setCustomerName(order.getCustomer().getName());
//		billDto.setDishName(order.getDish().getName());
//		
//		return billDto;
	}

	@Override
	public void updateOrderDeliveryStatus(long orderId, String newStatus) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(()->new RuntimeException("Order does not exist!"));
		order.setDeliveryStatus(DeliveryStatus.valueOf(newStatus));
		orderRepository.save(order);
	}
	
}
